package model;

import model.bookSearch.Queryable;
import model.bookSearch.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A book belonging to the model.BookwormLibrary. Books are created when their purchased, or loaded in from persistent data.
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class Book implements Queryable {

    //Required book data
    private String title;
    private String ISBN;
    private String publisher;
    private ArrayList < String > authors;

    private Date publishDate;

    private int pageCount;

    private int numberOfCopies = 0;
    private int numberOfCheckedOut = 0;

    /**
     *
     * @param isbn - The unique ISBN associated with this book
     * @param title - The title of this book
     * @param authors - The author(s) of this book
     * @param publisher - The publisher of this book
     * @param publishDate - The publish date of this book
     */
    public Book(String isbn, String title, ArrayList < String > authors, String publisher, Date publishDate, int pageCount) {
        this.ISBN = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
    }

    /**
     * Get this books Title
     * @return String of the Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get this books unique ISBN
     * @return String of the ISBN
     */
    public String getISBN() {
        return this.ISBN;
    }

    /**
     * Get this books Publisher
     * @return String name of the Publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Get this books list of authors
     * @return ArrayList of autors
     */
    public ArrayList < String > getAuthors() {
        return authors;
    }

    /**
     * Get this books published date
     * @return String representation of published date
     */
    public String getPublishDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.publishDate);
    }

    /**
     * Get this books number of pages
     * @return Int number of pages
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Get this books number of copies
     * @return Int number of copies
     */
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * Get this books number of checked out copies
     * @return Int number of checked out copies
     */
    public int getNumberOfCheckedOut() {
        return numberOfCheckedOut;
    }

    /**
     * Increment the number of this book that are checked out
     */
    public void checkOut() {
        if (numberOfCheckedOut < numberOfCopies) {
            this.numberOfCheckedOut++;
        }
    }

    /**
     * Decrement the number of this book that are checked out
     */
    public void checkIn() {
        if (numberOfCheckedOut > 0) {
            this.numberOfCheckedOut--;
        }
    }

    /**
     * Add to the number of copies that the library has of this book
     * @param amount number of copies
     */
    public void addCopies(int amount) {
        this.numberOfCopies += amount;
    }

    @Override
    public void doQuery(Query q) {
        q.query(this);
    }
}