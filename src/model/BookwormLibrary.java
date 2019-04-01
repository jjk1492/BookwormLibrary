package model;


import com.google.gson.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


import java.io.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents the Bookworm Library. Holds all the books, visitors, and visits to the library.
 * Utilizes the Singleton design pattern
 *
 * @author John Knecht V (jjk1492@rit.edu) & Lucas Golden
 */
public class BookwormLibrary {

    private static BookwormLibrary INSTANCE;

    private final static String BOOKS_FILE = "savedBooks.json";
    private final static String VISITORS_FILE = "savedVisitors.json";
    private final static String CHECKOUTS_FILE = "savedCheckOuts.json";
    private final static String DATETIME_FILE = "savedDateTime.json";

    private final static String FILE_PATH = "src/model/shutdownFiles/";

    private final static Gson gson = new Gson();

    //HashMap key is the ISBN of the book
    private HashMap < String, Book > books;

    //HashMap key is the visitors uniqueID
    private HashMap < Long, Visitor > visitors;

    //HashMap key is the visitors uniqueID
    private HashMap < Long, ArrayList < CheckOut >> checkedOutBooks;

    //ArrayList of current visitors in Library
    private ArrayList < Visit > currentVisits;

    private LocalDateTime time;

    private ArrayList<Book> catalogue;

    /**
     * Lazy constructor
     *
     * Create HashMaps for books and visitors
     */
    public BookwormLibrary() {
        if(INSTANCE == null) {
            INSTANCE = this;
            this.currentVisits = new ArrayList < > ();
            this.catalogue = BookStore.getInstance().loadLocal();

            //Try and read in previous books, if none are found create a new HashMap
            try{
                System.out.println("Loading previous data...");
                Type bookType = new TypeToken<HashMap<String, Book>>(){}.getType();
                JsonReader reader = new JsonReader(new FileReader(FILE_PATH + BOOKS_FILE));
                this.books = gson.fromJson(reader, bookType);
                reader.close();
                System.out.println("Books loaded.");
            }catch (Exception e){
                this.books = new HashMap<>();
                System.out.println("No books found.");
            }
            if(this.books == null){
                this.books = new HashMap<>();
            }

            //Try and read in previous visitors, if none are found create a new HashMap
            try{
                Type visitorType = new TypeToken<HashMap<Long, Visitor>>(){}.getType();
                JsonReader reader = new JsonReader( new FileReader(FILE_PATH + VISITORS_FILE));
                this.visitors = gson.fromJson(reader, visitorType);
                reader.close();
                System.out.println("Visitors loaded.");
            }catch (Exception e){
                this.visitors = new HashMap<>();
                System.out.println("No visitors found.");
            }
            if(this.visitors == null){
                this.visitors = new HashMap<>();
            }

            //Try and read in previous CheckOuts, if none are found create a new HashMap
            try{
                Type checkoutType = new TypeToken<HashMap<Long, ArrayList<CheckOut>>>(){}.getType();
                JsonReader reader = new JsonReader(new FileReader(FILE_PATH + CHECKOUTS_FILE));
                this.checkedOutBooks = gson.fromJson(reader, checkoutType);
                reader.close();
                System.out.println("Checkouts loaded.");

            }catch (Exception e){
                this.checkedOutBooks = new HashMap<>();
                System.out.println("No checkouts found.");
            }
            if(this.checkedOutBooks == null){
                this.checkedOutBooks = new HashMap<>();
            }

            try{
                Type dateTimeType = new TypeToken<LocalDateTime>(){}.getType();
                JsonReader reader = new JsonReader(new FileReader(FILE_PATH + DATETIME_FILE));
                this.time = gson.fromJson(reader, dateTimeType);
                reader.close();
                System.out.println("Time loaded.\n");
            }catch (Exception e){
                time = LocalDateTime.now();
                System.out.println("No saved time found, resetting time.\n");
            }
            if(this.time == null){
                time = LocalDateTime.now();
            }
        }
    }


    //VISITOR RELATED METHODS


    public ArrayList<Visit> getCurrentVisits() {
        return currentVisits;
    }

    /**
     * Add a visitor to currently visiting
     * @param visitorID Visitor
     * @return string of error or success
     */
    public Visit addToVisits(Long visitorID) {
        Visitor v = this.visitors.get(visitorID);
        Visit visit = new Visit(v, time);
        this.currentVisits.add(visit);
        return visit;
    }

    /**
     * Remove a user from currently visiting
     * @param visitorID Visitor
     * @return string of error or success
     */
    public Visit removeFromVisits(Long visitorID) {
        for (Visit vis: this.currentVisits) {
            if (visitorID.equals(vis.getVisitorId())) {
                vis.endVisit(time);
                this.currentVisits.remove(vis);
                return vis;
            }
        }
        return null;
    }

    /**
     * Verifies that a new user is not already contained in the system.
     * @param firstName - Visitor's first name
     * @param lastName - Visitor's last name
     * @param address - Visitor's address
     * @param phoneNumber - Visitor's phone number
     * @return True if the the user can register, false otherwise
     */
    public boolean verifyUser(String firstName, String lastName, String address, String phoneNumber) {
        Visitor temp = new Visitor(firstName, lastName, address, phoneNumber, 1111111111L, time);
        return !this.visitors.containsValue(temp);
    }

    /**
     * Registers a new user and adds them to the library
     * @param firstName - Visitor's first name
     * @param lastName - Visitor's last name
     * @param address - Visitor's address
     * @param phoneNumber - Visitor's phone number
     */
    public Visitor registerUser(String firstName, String lastName, String address, String phoneNumber) {
        Long userID = getUnusedVisitorID();
        Visitor newVisitor = new Visitor(firstName, lastName, address, phoneNumber, userID, time);
        visitors.put(userID, newVisitor);
        return newVisitor;
    }

    /**
     * Generates a unique visitorID
     * @return A unique visitorID as a long
     */
    private Long getUnusedVisitorID(){
        Random r = new Random();
        while (true){
            long id = (long)(r.nextDouble() * 8999999999L) + 1000000000;
            if(!visitors.containsKey(id)){
                return id;
            }
        }
    }

    public boolean hasVisitor(Long visitorID){
        return this.visitors.containsKey(visitorID);
    }


    //BOOK RELATED METHODS

    public void setCatalogue(ArrayList<Book> newCatalogue){
        this.catalogue = newCatalogue;
    }

    /**
     * Checks out a book from the library
     * @param visitorID - Visitor requesting a book check out
     * @param bookIDs - Book being checked out
     * @return boolean if the checkout was successful
     */
    public String checkOut(Long visitorID, ArrayList < String > bookIDs) {

        //Ensure the requested book and user are in the system
        if (visitors.containsKey(visitorID)) {

            if (checkedOutBooks.containsKey(visitorID)) {
                if (checkedOutBooks.get(visitorID).size() + bookIDs.size() > 5) {
                    return "max-checkout-exceeded";
                }
            }

            //Ensure requested books are owned by bookworm library
            ArrayList < String > bookReturn = new ArrayList < > ();
            for (String bookID: bookIDs) {
                if (!this.books.containsKey(bookID)) {
                    bookReturn.add(bookID);
                }
            }

            if (bookReturn.size() > 0) {
                return "invalid-book-id," + "{" + String.join(",", bookReturn) + "}";
            }

            ArrayList < CheckOut > checkOuts = new ArrayList < > ();
            for (String bookID: bookIDs) {
                Book toCheckout = this.books.get(bookID);
                toCheckout.checkOut();
                checkOuts.add(new CheckOut(toCheckout, visitorID, Calendar.getInstance()));
            }

            this.checkedOutBooks.put(visitorID, checkOuts);
            return checkOuts.get(0).getDueDate();
        }
        return "invalid-visitor-id";
    }


    /**
     * Check in a book, when books are checked in their validated, checked-in, then return a fine if the any books are overdue.
     * @param visitorID - ID of the visitor checkin in a book
     * @param bookIDs - Array of book ISBNs that are being checked in by the visitor
     * @return - Stringing explaining the error or telling the fee amount.
     */
    public String checkIn(Long visitorID, ArrayList < String > bookIDs) {

        if (visitors.containsKey(visitorID)) {
            String invalidIDs = "";
            for (String isbn: bookIDs) {
                if (!books.containsKey(isbn)) {
                    invalidIDs = invalidIDs.concat(isbn + ",");
                }
            }
            if (invalidIDs.length() > 0) {
                invalidIDs = invalidIDs.substring(0, invalidIDs.length() - 1);
                return "invalid-book-id," + invalidIDs;
            }

            String overDue = "";
            for (String isbn: bookIDs) {
                for (CheckOut co: this.checkedOutBooks.get(visitorID)) {
                    if (co.isOverDue()) {
                        overDue = overDue.concat(isbn + ",");
                        this.visitors.get(visitorID).addFine(co.getFine());
                    }

                    if (co.getBook().getISBN().equals(isbn)) {
                        this.checkedOutBooks.get(visitorID).remove(co);
                        this.books.get(isbn).checkIn();
                    }
                }
            }

            if (overDue.length() > 0) {
                return "overdue,$" + visitors.get(visitorID).getFine() + overDue.substring(0, overDue.length() - 1);
            }

            return "success";
        } else {
            return "invalid-visitor-id";
        }
    }

    /**
     * Purchase a book from the catalogue
     * @param isbn Book ISBN number
     * @param quantity number to purchase
     * @return string of error or success
     */
    public boolean buyBook(String isbn, int quantity) {
        boolean successful = false;

        for( Book book : this.catalogue){
            if( book.getISBN().equals(isbn) ){
                if( this.books.containsKey(isbn) ){
                    this.books.get(isbn).addCopies(quantity);
                }else {
                    Book toPurchase = new Book(book.getISBN(), book.getTitle(), book.getAuthors(), book.getPublisher(), book.getPublishDate(), book.getPageCount());
                    toPurchase.addCopies(quantity);
                    this.books.put(isbn, toPurchase);
                }
                successful = true;
            }
        }

        return successful;
    }

    public void removeBook(String isbn, int quantity){
        this.books.get(isbn).removeQuantities(quantity);
    }

    public ArrayList<CheckOut> getVisitorCheckOuts(Long visitorID){
        ArrayList<CheckOut> visitorsCheckouts = new ArrayList<>();

        if (this.checkedOutBooks.containsKey(visitorID)) {
            visitorsCheckouts = this.checkedOutBooks.get(visitorID);
        }

        return visitorsCheckouts;
    }


    //TIME RELATED METHODS

    public void advanceTimeDays(int amount){
        time = time.plusDays(amount);
    }

    public void advanceTimeHours(int amount){
        time = time.plusHours(amount);
    }

    //SHUTDOWN RELATED METHODS

    /**
     * Shuts down the system, storing all data in a txt file
     */
    public void shutdown(){

        //End all on going visits
        for(Visit v : this.currentVisits) {
            v.endVisit(time);
        }

        //Clear all current visits now that they're ended.
        currentVisits.clear();

        //Convert all required data structures to JSON
        Gson gsonBuilder = new GsonBuilder().create();

        try{
            Writer writer = new PrintWriter(FILE_PATH + BOOKS_FILE, "UTF-8");
            gsonBuilder.toJson(this.books, writer);
            writer.close();

            writer = new PrintWriter(FILE_PATH + VISITORS_FILE, "UTF-8");
            gsonBuilder.toJson(this.visitors, writer);
            writer.close();

            writer = new PrintWriter(FILE_PATH + CHECKOUTS_FILE, "UTF-8");
            gsonBuilder.toJson(this.checkedOutBooks, writer);
            writer.close();

            writer = new PrintWriter(FILE_PATH + DATETIME_FILE, "UTF-8");
            gsonBuilder.toJson(this.time, writer);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //GETTERS

    /**
     * Retrieve the one and only instance of the model.BookwormLibrary.
     * @return The instance of the model.BookwormLibrary
     */
    public static BookwormLibrary getInstance() {
        return INSTANCE;
    }

    /**
     * Get all of the visitors that have visited the library
     * @return Hasmap<VisitorId, Visitor>
     */
    public HashMap < Long, Visitor > getVisitors() {
        return visitors;
    }

    /**
     * Get all the books the library currently owns
     * @return ArrayList<Book> of all the books the library owns
     */
    public HashMap < String, Book > getBooks() {
        return books;
    }

    /**
     * Get the books each member has checked out
     * @return HashMap<VisitorID, ArrayList<Checkout>> of all checked out books
     */
    public HashMap < Long, ArrayList < CheckOut >> getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Visitor getVisitor(Long visitorID){
        return this.visitors.get((visitorID));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookwormLibrary that = (BookwormLibrary) o;
        return books.equals(that.books) &&
                visitors.equals(that.visitors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, visitors);
    }
}