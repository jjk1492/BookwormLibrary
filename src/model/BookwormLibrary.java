package model;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.io.File;
import java.text.SimpleDateFormat;
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
    //ArrayList of purchasable books
    private ArrayList < Book > catalogue;

    //HashMap key is the ISBN of the book
    private HashMap < String, Book > books;

    //HashMap key is the visitors uniqueID
    private HashMap < Long, Visitor > visitors;

    private List<Long> usedVisitorIds;

    //HashMap key is the visitors uniqueID
    private HashMap < Long, ArrayList < CheckOut >> checkedOutBooks;

    //ArrayList of current visitors in Library
    private ArrayList < Visit > currentVisits;

    private LocalDateTime time;

    /**
     * Lazy constructor
     *
     * Create HashMaps for books and visitors
     */
    public BookwormLibrary() {
        if(INSTANCE == null) {
            INSTANCE = this;
            if(!loadDateTime()){
                time = LocalDateTime.now();
            }
            this.catalogue = this.readInBooks();
            this.books = new HashMap < > ();
            this.visitors = new HashMap < > ();
            this.usedVisitorIds = new ArrayList<>();
            this.checkedOutBooks = new HashMap < > ();
            this.currentVisits = new ArrayList < > ();
        }
    }

    /**
     * Test constructor, allows pre-constructed Visitor and Book HashMaps
     * @param books - HashMap for the books
     * @param visitors - HashMap for the visitors
     */
    public BookwormLibrary(HashMap < String, Book > books, HashMap < Long, Visitor > visitors, HashMap < Long, ArrayList < CheckOut >> checkedOutBooks, ArrayList < Visit > currentVisits) {
        this.books = books;
        this.visitors = visitors;
        this.checkedOutBooks = checkedOutBooks;
        this.currentVisits = currentVisits;
    }


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
     * Get the books that can be purchased from the catalogue
     * @return ArrayList<BooK> of all the books that can be purchased
     */
    public ArrayList < Book > getCatalogue() {
        return catalogue;
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

    public void advanceTimeDays(int amount){
        time = time.plusDays(amount);
    }

    public void advanceTimeHours(int amount){
        time = time.plusHours(amount);
    }

    private boolean loadDateTime(){
        try {
            Gson gson = new Gson();
            time = gson.fromJson(DATETIME_FILE, LocalDateTime.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Add a visitor to currently visiting
     * @param visitorID Visitor
     * @return string of error or success
     */
    public String addToVisits(Long visitorID) {
        Visitor v = this.visitors.get(visitorID);
        if (v == null) {
            return "invalid-id";
        }
        for (Visit vis: this.currentVisits) {
            if (visitorID.equals(vis.getVisitorId())) {
                return "arrive,duplicate";
            }
        }
        Visit visit = new Visit(v);
        this.currentVisits.add(visit);
        return "arrive," + visitorID + "," + visit.getStartDate() + "," + visit.getStartTime();
    }

    /**
     * Remove a user from currently visiting
     * @param visitorID Visitor
     * @return string of error or success
     */
    public String removeFromVisits(Long visitorID) {
        for (Visit vis: this.currentVisits) {
            if (visitorID.equals(vis.getVisitorId())) {
                vis.endVisit();
                this.currentVisits.remove(vis);
                return "depart," + vis.getVisitorId() + "," + vis.getEndTime() + "," + vis.getVisitTimeMinutes();
            }
        }
        return "invalid-id";
    }

    /**
     * Read in books from catalogue
     * Only used in initializing the books field
     * @return ArrayList<Book> of all books in the catalogue
     */
    private ArrayList < Book > readInBooks() {
        ArrayList < Book > books = new ArrayList < > ();
        try {
            Scanner scanner = new Scanner(new File("./src/books.txt"));

            while (scanner.hasNextLine()) {
                ArrayList < String > vals = new ArrayList < > ();
                StringBuilder builder = new StringBuilder();

                boolean inSpecial = false;
                for (char c: scanner.nextLine().toCharArray()) {
                    if (c == '"' || c == '{' || c == '}') {
                        inSpecial = !inSpecial;
                    } else if (c == ',' && !inSpecial) {
                        vals.add(builder.toString());
                        builder = new StringBuilder();
                    } else {
                        builder.append(c);
                    }
                }

                vals.add(builder.toString());

                if (vals.get(4).length() == 4) {
                    vals.set(4, vals.get(4) + "-01-01");
                } else if (vals.get(4).length() == 7) {
                    vals.set(4, vals.get(4) + "-01");
                }

                books.add(new Book(vals.get(0), vals.get(1), new ArrayList < > (Arrays.asList(vals.get(2).split(","))), vals.get(3), new SimpleDateFormat("yyyy-MM-dd").parse(vals.get(4)), Integer.parseInt(vals.get(5))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
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

    private Long getUnusedVisitorID(){
        Random r = new Random();
        while (true){
            long id = (long)(r.nextDouble() * 8999999999L) + 1000000000;
            if(!usedVisitorIds.contains(id)){
                usedVisitorIds.add(id);
                return id;
            }
        }
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
     * Shuts down the system, storing all data in a txt file
     */
    public void shutdown(){

        //End all on going visits
        for(Visit v : this.currentVisits) {
            v.endVisit();
        }

        //Clear all current visits now that they're ended.
        currentVisits.clear();

        //Convert all required data structures to JSON
        Gson gson = new Gson();
        String booksJSON = gson.toJson(this.books);
        String visitorsJSON = gson.toJson(this.visitors);
        String checkOutsJSON = gson.toJson(this.checkedOutBooks);
        String dateTimeJSON = gson.toJson(this.time);

        //Save all the data to their own files
        saveData(BOOKS_FILE, booksJSON);
        saveData(VISITORS_FILE, visitorsJSON);
        saveData(CHECKOUTS_FILE, checkOutsJSON);
        saveData(DATETIME_FILE, dateTimeJSON);
    }

    /**
     * Saves the JSON representation of an object to a file for persistent system data.
     * @param fileName - Name of the file to save to
     * @param fileData - JSON representation of a data structure
     */
    private void saveData(String fileName, String fileData){
        try{
            PrintWriter writer = new PrintWriter("src/model/shutdownFiles/" + fileName, "UTF-8");
            writer.println(fileData);
            writer.close();
        }catch (FileNotFoundException e) {
            System.out.println("Error: " + fileName + " could not be found.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
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
    public String buyBook(String isbn, int quantity) {
        if (books.containsKey(isbn)) {
            books.get(isbn).addCopies(quantity);
            return books.get(isbn).toString();
        } else {
            for (Book b: catalogue) {
                if (b.getISBN().equals(isbn)) {
                    try {
                        Book b1 = new Book(b.getISBN(), b.getTitle(), b.getAuthors(), b.getPublisher(), new SimpleDateFormat("yyyy-MM-dd").parse(b.getPublishDate()), b.getPageCount());
                        b1.addCopies(quantity);
                        books.put(isbn, b1);
                        return b1.getISBN() + "," + b1.getTitle() + "," + String.join(",", b1.getAuthors()) + "," + new SimpleDateFormat("yyyy-MM-dd").format(b1.getPublishDate()) + "," + b1.getNumberOfCopies();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "";
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