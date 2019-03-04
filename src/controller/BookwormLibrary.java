package controller;

import model.Book;
import model.CheckOut;
import model.Visit;
import model.Visitor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents the Bookworm Library. Holds all the books, visitors, and visits to the library.
 * Utilizes the Singleton design pattern
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class BookwormLibrary {

    private static BookwormLibrary INSTANCE = new BookwormLibrary();

    private final static int MAX_NUM_CHECKOUTS = 5;

    //Book return messages. Format "Result:Message:Data" Result can be Overdue, Success, or Error | Message is the message associated with
    //the result | Data is data needed to be reported by the system.i.e. invalid book IDs
    private final static String OVERDUE = "overdue";
    private final static String SUCCESS = "success";
    private final static String INVALID_VISITOR = "invalid-visitor-id";
    private final static String INVALID_BOOK = "invalid-book-id,";
    private final static String MAX_CHECKOUTS_EXCEEDED = "max-checkout-exceeded";

    //Arraylist of purchasable books
    private ArrayList<Book> catalogue = this.readInBooks();

    //HashMap key is the ISBN of the book
    private HashMap<String, Book> books;

    //HashMap key is the visitors uniqueID
    private HashMap<String, Visitor> visitors;

    //HashMap key is the visitors uniqueID
    private HashMap<String, ArrayList<CheckOut>> checkedOutBooks;

    private ArrayList<Visit> currentVisits;

    /**
     * Lazy constructor
     *
     * Create HashMaps for books and visitors
     */
    private BookwormLibrary(){
        this.books = new HashMap<>();
        this.visitors = new HashMap<>();
        this.checkedOutBooks = new HashMap<>();
    }

    /**
     * Test constructor, allows pre-constructed Visitor and Book HashMaps
     * @param books - HashMap for the books
     * @param visitors - HashMap for the visitors
     */
    public BookwormLibrary(HashMap<String, Book> books, HashMap<String, Visitor> visitors, HashMap<String, ArrayList<CheckOut>> checkOuts){
        this.books = books;
        this.visitors = visitors;
        this.checkedOutBooks = checkOuts;
    }


    /**
     * Retrieve the one and only instance of the controller.BookwormLibrary.
     * @return The instance of the controller.BookwormLibrary
     */
    public static BookwormLibrary getInstance(){
        return INSTANCE;
    }

    public HashMap<String, Visitor> getVisitors() {
        return visitors;
    }
  
    public ArrayList<Book> getCatalogue() {
        return catalogue;
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }

    public HashMap<String, ArrayList<CheckOut>> getCheckedOutBooks() {
        return checkedOutBooks;
    }
  
    public ArrayList<Visit> getCurrentVisits() {
        return currentVisits;
    }

    public void addToVisits(Visit v){
        currentVisits.add(v);
    }

    public void removeFromVisits(Visit v){
        currentVisits.remove(v);
    }

    private ArrayList<Book> readInBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("./src/books.txt"));

            while(scanner.hasNextLine()) {
                ArrayList<String> vals = new ArrayList<>();
                StringBuilder builder = new StringBuilder();

                boolean inSpecial = false;
                for (char c : scanner.nextLine().toCharArray()) {
                    if (c == '"' || c == '{' || c == '}') {
                        inSpecial = !inSpecial;
                    }
                    else if (c == ',' && !inSpecial) {
                        vals.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }

                vals.add(builder.toString());

                if (vals.get(4).length() == 4) {
                    vals.set(4, vals.get(4) + "-01-01");
                }
                else if (vals.get(4).length() == 7) {
                    vals.set(4, vals.get(4) + "-01");
                }

                books.add(new Book(vals.get(0), vals.get(1), new ArrayList<>(Arrays.asList(vals.get(2).split(","))), vals.get(3), new SimpleDateFormat("yyyy-MM-dd").parse(vals.get(4)), Integer.parseInt(vals.get(5))));
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
    public boolean verifyUser(String firstName, String lastName, String address, String phoneNumber){
        Visitor temp = new Visitor(firstName, lastName, address, phoneNumber, "TestUser");
        return !this.visitors.containsValue(temp);
    }

    /**
     * Registers a new user and adds them to the library
     * @param firstName - Visitor's first name
     * @param lastName - Visitor's last name
     * @param address - Visitor's address
     * @param phoneNumber - Visitor's phone number
     */
    public String registerUser(String firstName, String lastName, String address, String phoneNumber) {
        if (verifyUser(firstName, lastName, address, phoneNumber)) {
            String userID = Long.toString(new Random().nextLong());
            Visitor newVisitor = new Visitor(firstName, lastName, address, phoneNumber, userID);
            visitors.put(userID, newVisitor);
            return "register," + userID + "," + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ";";
        } else {
            return "register,duplicate;";
        }
    }

    /**
     * Checks out a book from the library
     * @param visitorID - Visitor requesting a book check out
     * @param bookIDs - Book being checked out
     * @return boolean if the checkout was successful
     */
    public String checkOut(String visitorID, ArrayList<String> bookIDs){

        //Ensure the requested book and user are in the system
        if( visitors.containsKey(visitorID) ){

            if( checkedOutBooks.containsKey(visitorID) ){
                if( checkedOutBooks.get(visitorID).size() + bookIDs.size() > MAX_NUM_CHECKOUTS ){
                    return MAX_CHECKOUTS_EXCEEDED;
                }
            }

            //Ensure requested books are owned by bookworm library
            ArrayList<String> bookReturn = new ArrayList<>();
            for( String bookID : bookIDs ){
               if( !this.books.containsKey(bookID) ){
                   bookReturn.add(bookID);
               }
            }

            if( bookReturn.size() > 0 ){
                return INVALID_BOOK + "{" + String.join(",", bookReturn) + "}";
            }

            ArrayList<CheckOut> checkOuts = new ArrayList<>();
            for( String bookID : bookIDs ){
                Book toCheckout = this.books.get(bookID);
                toCheckout.checkOut();
                checkOuts.add(new CheckOut(toCheckout, visitorID, Calendar.getInstance()));
            }

            this.checkedOutBooks.put(visitorID, checkOuts);
            return checkOuts.get(0).getDueDate();
        }
        return INVALID_VISITOR;
    }

    /**
     * Check in a book, when books are checked in their validated, checked-in, then return a fine if the any books are overdue.
     * @param visitorID - ID of the visitor checkin in a book
     * @param bookIDs - Array of book ISBNs that are being checked in by the visitor
     * @return - Stringing explaining the error or telling the fee amount.
     */
    public String checkIn(String visitorID, ArrayList<String> bookIDs){

        if (visitors.containsKey(visitorID)) {
            String invalidIDs = "";
            for(String isbn: bookIDs) {
                if (!books.containsKey(isbn)) {
                    invalidIDs += (isbn + ",");
                }
            }
            if (invalidIDs.length() > 0) {
                invalidIDs = invalidIDs.substring(0, invalidIDs.length() - 1);
                return "invalid-book-id," + invalidIDs;
            }

            String overDue = "";
            for(String isbn: bookIDs) {
                for(CheckOut co : this.checkedOutBooks.get(visitorID)) {
                    if (co.isOverDue()) {
                        overDue += (isbn + ",");
                        this.visitors.get(visitorID).addFine(co.getFine());
                    }

                    if (co.getBook().getISBN().equals(isbn)) {
                        this.checkedOutBooks.get(visitorID).remove(co);
                        this.books.get(isbn).checkIn();
                    }
                }
            }

            if (overDue.length() > 0) {
                return OVERDUE + ",$" + visitors.get(visitorID).getFine() + overDue.substring(0, overDue.length() - 1);
            }

            return SUCCESS;
        }
        else {
            return INVALID_VISITOR;
        }
    }

    public String buyBook(String isbn, int quantity){
        if(books.containsKey(isbn)){
            books.get(isbn).addCopies(quantity);
            return books.get(isbn).toString();
        }else{
            for(Book b : catalogue){
                if(b.getISBN().equals(isbn)){
                    try {
                        Book b1 = new Book(b.getISBN(), b.getTitle(), b.getAuthors(), b.getPublisher(), new SimpleDateFormat("yyyy-MM-dd").parse(b.getPublishDate()), b.getPageCount());
                        b1.addCopies(quantity);
                        books.put(isbn, b1);
                        return b1.toString();
                    }catch (Exception e){
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
