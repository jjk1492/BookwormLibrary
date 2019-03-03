package Controller;

import controller.BookwormLibrary;
import model.Book;
import model.CheckOut;
import model.Visit;
import model.Visitor;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

/**
 * J-Unit tests for the BookwormLibrary class.
 *
 * @author John Knecht V (jjk1492@rit.edu) & Lucas Golden
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
        HashMap<String, ArrayList<CheckOut>> checkOuts = new HashMap<>();

        //Create a user and add them to the library
        Visitor bruce = new Visitor("Bruce", "Wayne", "1 Batcave Lane", "555-555-5555", "1");
        visitors.put(bruce.getUserID(), bruce);
        BookwormLibrary cut = new BookwormLibrary(books, visitors, checkOuts);

        //Assert that a new user would be able to register
        assertTrue(cut.verifyUser("Tom", "Jerry", "5 Meadow Lane", "696-969-6969"));

        //Assert that a user with the same info as a user in the library cannot register
        assertFalse(cut.verifyUser("Bruce", "Wayne", "1 Batcave Lane", "555-555-5555"));

    }

    @Test
    public void registerUserTest(){

        //Create an expected outcome bookworm library
        HashMap<String, Visitor> expectedVisitors = new HashMap<>();
        Visitor CeeMoore = new Visitor("CeeMoore", "Johnson", "123 Nowhere Road", "609-609-0609", "1");
        expectedVisitors.put(CeeMoore.getUserID(), CeeMoore);

        //Initialize the HashMaps for books and visitors
        HashMap<String, Visitor> visitors = new HashMap<>();
        HashMap<String, Book> books = new HashMap<>();
        HashMap<String, ArrayList<CheckOut>> checkOuts = new HashMap<>();

        BookwormLibrary expected = new BookwormLibrary(books, expectedVisitors, checkOuts);

        //Create a user and add them to the library
        BookwormLibrary cut = new BookwormLibrary(books, visitors, checkOuts);

        cut.registerUser("CeeMoore", "Johnson", "123 Nowhere Road", "609-609-0609", "1");

        assertEquals(expected, cut);

    }

    @Test
    public void checkOutTest(){

        //Create an expected outcome bookworm library
        HashMap<String, Visitor> expectedVisitors = new HashMap<>();
        HashMap<String, Book> expectedBooks = new HashMap<>();
        HashMap<String, ArrayList<CheckOut>> expectedCheckOuts = new HashMap<>();

        Visitor Donald = new Visitor("Donald", "Trump", "1 White House Road", "TRU-FRE-EDOM", "1");
        expectedVisitors.put(Donald.getUserID(), Donald);

        ArrayList<String> expectedAuthors = new ArrayList<>();
        expectedAuthors.add("Herman Melville");
        Book book = new Book("9780553213119","Moby Dick", expectedAuthors,"Richard Bentley", "1851",3, 1);

        expectedBooks.put("9780553213119", book);

    }

    @Test
    public void testSafeShutDown(){
        //setting up environment
        HashMap<String, Visitor> expectedVisitors = new HashMap<>();
        Visitor CeeMoore = new Visitor("CeeMoore", "Johnson", "123 Nowhere Road", "609-609-0609", "1");
        ArrayList<Visit> v = new ArrayList<>();
        v.add(new Visit(Calendar.getInstance().getTimeInMillis(), "Feb 2nd", CeeMoore));
        HashMap<String, Visitor> visitors = new HashMap<>();
        visitors.put("CeeMoore", CeeMoore);
        HashMap<String, Book> books = new HashMap<>();
        ArrayList<String> expectedAuthors = new ArrayList<>();
        Book book = new Book("9780553213119","Moby Dick", expectedAuthors,
                "Richard Bentley", "1851",3, 1);
        books.put("Moby Dick", book);
        HashMap<String, ArrayList<CheckOut>> checkOuts = new HashMap<>();
        BookwormLibrary cut = new BookwormLibrary(books, visitors, checkOuts, v);
        cut.shutdown(); //execute shutdown
        //make sure removed all visits
        assertTrue(v.size() == 0);
    }
}
