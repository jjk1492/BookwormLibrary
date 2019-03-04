package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A book belonging to the controller.BookwormLibrary. Books are created when their purchased, or loaded in from persistent data.
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class Book{

    //Required book data
    private String title;
    private String ISBN;
    private String publisher;
    private ArrayList<String> authors;

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
    public Book(String isbn, String title, ArrayList<String> authors, String publisher, Date publishDate, int pageCount) {
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
    public String getISBN(){
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
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * Get this books published date
     * @return String representation of published date
     */
    public String getPublishDate() {
        return publishDate.toString();
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
    public void checkOut(){
        if( numberOfCheckedOut < numberOfCopies){
            this.numberOfCheckedOut++;
        }
    }

    /**
     * Decrement the number of this book that are checked out
     */
    public void checkIn(){
        if( numberOfCheckedOut > 0){
            this.numberOfCheckedOut--;
        }
    }

    @Override
    public String toString() {
        return this.getISBN() + " " + this.title + " " + this.authors + " " + this.publisher + " " + this.publishDate +
                " " + this.numberOfCopies + " " + this.numberOfCheckedOut;
    }
}
