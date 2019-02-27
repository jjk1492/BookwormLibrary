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

    //HashMap key is the ISBN of the book
    private HashMap<String, Book> books;

    //HashMap key is the visitors uniqueID
    private HashMap<String, Visitor> visitors;

    /**
     * Lazy constructor
     */
    private BookwormLibrary(){
        this.books = new HashMap<>();
        this.visitors = new HashMap<>();
    }

    /**
     * Creates one instance of the controller.BookwormLibrary
     */
    private static class LibraryInstance{
        private static final BookwormLibrary INSTANCE = new BookwormLibrary();
    }

    /**
     * Retrieve the one and only instance of the controller.BookwormLibrary.
     * @return The instance of the controller.BookwormLibrary
     */
    public static BookwormLibrary getInstance(){
        return LibraryInstance.INSTANCE;
    }

    public boolean registerUser(String firstName, String lastName, String address, String phoneNumber, String userID){
        return false;
    }

}
