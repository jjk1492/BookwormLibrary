package controller;

import model.Book;
import model.Visitor;

import java.util.HashMap;

/**
 * Represents the Bookworm Library. Holds all the books, visitors, and visits to the library.
 * Utilizes the Singleton design pattern
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class BookwormLibrary {

    private static BookwormLibrary INSTANCE = new BookwormLibrary();

    //HashMap key is the ISBN of the book
    private HashMap<String, Book> books;

    //HashMap key is the visitors uniqueID
    private HashMap<String, Visitor> visitors;

    /**
     * Lazy constructor
     *
     * Create HashMaps for books and visitors
     */
    private BookwormLibrary(){
        this.books = new HashMap<>();
        this.visitors = new HashMap<>();
    }

    /**
     * Test constructor, allows pre-constructed Visitor and Book HashMaps
     * @param books - HashMap for the books
     * @param visitors - HashMap for the visitors
     */
    public BookwormLibrary(HashMap<String, Book> books, HashMap<String, Visitor> visitors){
        this.books = books;
        this.visitors = visitors;
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
        if( this.visitors.containsValue(temp) ){
            return false;
        }
        return true;
    }

    /**
     * Registers a new user and adds them to the library
     * @param firstName - Visitor's first name
     * @param lastName - Visitor's last name
     * @param address - Visitor's address
     * @param phoneNumber - Visitor's phone number
     */
    public void registerUser(String firstName, String lastName, String address, String phoneNumber, String userID){

    }

}
