package Controller;

import controller.BookwormLibrary;
import model.Book;
import model.Visitor;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * J-Unit tests for the BookwormLibrary class.
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
@Tag("controller-tier")
public class BookwormLibraryTest {

    /**
     * Tests users registering for the library. Assert true is returned for new users, and false is returned for existing users.
     */
    @Test
    public void verifyUserTest(){

        //Initialize the HashMaps for books and visitors
        HashMap<String, Visitor> visitors = new HashMap<>();
        HashMap<String, Book> books = new HashMap<>();

        //Create a user and add them to the library
        Visitor bruce = new Visitor("Bruce", "Wayne", "1 Batcave Lane", "555-555-5555", "1");
        visitors.put(bruce.getUserID(), bruce);
        BookwormLibrary cut = new BookwormLibrary(books, visitors);

        //Assert that a new user would be able to register
        assertTrue(cut.verifyUser("Tom", "Jerry", "5 Meadow Lane", "696-969-6969"));

        //Assert that a user with the same info as a user in the library cannot register
        assertFalse(cut.verifyUser("Bruce", "Wayne", "1 Batcave Lane", "555-555-5555"));

    }

    @Test
    public void registerUserTest(){

        //Initialize the HashMaps for books and visitors
        HashMap<String, Visitor> visitors = new HashMap<>();
        HashMap<String, Book> books = new HashMap<>();

        //Create an expected outcome bookworm library
        HashMap<String, Visitor> expectedVisitors = new HashMap<>();
        Visitor CeeMoore = new Visitor("CeeMoore", "Johnson", "123 Nowhere Road", "609-609-0609", "1");
        expectedVisitors.put(CeeMoore.getUserID(), CeeMoore);

        BookwormLibrary expected = new BookwormLibrary(books, expectedVisitors);

        //Create a user and add them to the library
        BookwormLibrary cut = new BookwormLibrary(books, visitors);

        cut.registerUser("CeeMoore", "Johnson", "123 Nowhere Road", "609-609-0609", "1");

        assertEquals(expected, cut);

    }


}
