package model;

import java.util.ArrayList;

/**
 * A book belonging to the controller.BookwormLibrary. Books are created when their purchased, or loaded in from persistent data.
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class Book{

    //Required book variables
    private String ISBN;
    private String title;
    private ArrayList<String> authors;
    private String publisher;
    private String publishDate;
    private int numberOfCopies;
    private int numberOfCheckedOut;

    //Used for fine tracking
    private String checkOutDate;
    private String checkInDate;


    /**
     *
     * @param isbn - The unique ISBN associated with this book
     * @param title - The title of this book
     * @param authors - The author(s) of this book
     * @param publisher - The publisher of this book
     * @param publishDate - The publish date of this book
     * @param numberOfCopies - Number of copies owned by controller.BookwormLibrary
     * @param numberOfCheckedOut - Number of copies currently checked out
     */
    public Book(String isbn, String title, ArrayList<String> authors, String publisher, String publishDate, int numberOfCopies, int numberOfCheckedOut) {
        this.ISBN = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numberOfCopies = numberOfCopies;
        this.numberOfCheckedOut = numberOfCheckedOut;

    }

    /**
     * Get this books unique ISBN
     * @return String of the ISBN
     */
    public String getISBN(){
        return this.ISBN;
    }

}
