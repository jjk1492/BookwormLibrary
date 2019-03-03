package controller;

import model.Book;
import model.CheckOut;
import model.Visitor;

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
    private final static double NO_FINE = 0.00;

    //Book return messages. Format "Result:Message:Data" Result can be Overdue, Success, or Error | Message is the message associated with
    //the result | Data is data needed to be reported by the system.i.e. invalid book IDs
    private final static String OVERDUE_BOOK = "Overdue:";
    private final static String SUCCESS = "Success";
    private final static String NOT_OWN_BOOK = "Error:You must return your own book.";
    private final static String INVALID_VISITOR = "Error:invalid-visitor-id.";
    private final static String NO_CHECKOUTS = "Error:Visitor doesn't have any books checked out.";
    private final static String INVALID_BOOK = "Error:invalid-book-id:";
    private final static String MAX_CHECKOUTS_EXCEEDED = "Error:max-checkout-exceeded";

    //HashMap key is the ISBN of the book
    private HashMap<String, Book> books;

    //HashMap key is the visitors uniqueID
    private HashMap<String, Visitor> visitors;

    private HashMap<String, ArrayList<CheckOut>> checkedOutBooks;

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
    public void registerUser(String firstName, String lastName, String address, String phoneNumber, String userID){
        if( verifyUser(firstName, lastName, address, phoneNumber)){
            Visitor newVisitor = new Visitor(firstName, lastName, address, phoneNumber, userID);
            visitors.put(userID, newVisitor);
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
                return INVALID_BOOK + bookReturn.toString();
            }

            ArrayList<CheckOut> checkOuts = new ArrayList<>();
            for( String bookID : bookIDs ){
                Book toCheckout = this.books.get(bookID);
                toCheckout.checkOut();
                checkOuts.add(new CheckOut(toCheckout, this.visitors.get(visitorID), Calendar.getInstance()));
            }

            this.checkedOutBooks.put(visitorID, checkOuts);
            return SUCCESS;
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

        //Check that the visitor is part of this library
        if( visitors.containsKey(visitorID) ){

            //Check the visitor has books checked out
            if( checkedOutBooks.containsKey(visitorID) ){

                //Check that the books requested to return are books owned by the library
                ArrayList<String> bookReturn = new ArrayList<>();
                for( String bookID : bookIDs){
                    if( !books.containsKey(bookID) ){
                        bookReturn.add(bookID);
                    }
                }

                //Return any invalid books trying to be returned
                if( bookReturn.size() > 0 ){
                    return INVALID_BOOK + bookReturn.toString();
                }

                //Use a stack to track which books need to be checked out
                Stack<CheckOut> checkIns = new Stack<>();
                ArrayList<CheckOut> visitorBooks = this.checkedOutBooks.get(visitorID);

                //Grab each book to be checked in
                for( CheckOut toReturn : visitorBooks ){

                    if( checkIns.size() == bookIDs.size() ){
                        break;
                    }

                    if(bookIDs.contains(toReturn.getBook())){
                        checkIns.push(toReturn);
                    }
                }

                //Ensure that the number of books being checked in matches the number of books from the books being checked out
                //owned by the visitor
                if(checkIns.size() != bookIDs.size() ){
                    while ( !checkIns.empty() ){
                        CheckOut checkOut = checkIns.pop();
                        if( !visitorBooks.contains( checkOut ) )  {
                            bookReturn.add(checkOut.getBook());
                        }
                    }
                    return NOT_OWN_BOOK + bookReturn.toString();
                }
                else {
                    //For each check-in remove it form the visitors list of checked out books
                    while ( !checkIns.empty() ){
                        CheckOut toRemove = checkIns.pop();
                        toRemove.checkIn();
                        this.books.get(toRemove.getBook()).checkIn();
                        visitorBooks.remove(toRemove);
                    }

                    //If the visitor still has books out, put them back in the hash
                    if( visitorBooks.size() > 0){
                        this.checkedOutBooks.put(visitorID, visitorBooks);
                    }

                    return SUCCESS;
                }
            }else{
                return NO_CHECKOUTS;
            }
        }
        return INVALID_VISITOR;
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
