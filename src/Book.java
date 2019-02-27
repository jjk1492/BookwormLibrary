import java.util.ArrayList;

public class Book {

    private String isbn;
    private String title;
    private ArrayList<String> authors;
    private String publisher;
    private String publishDate;
    private int numberOfCopies;
    private int numberOfCheckedOut;

    public Book(String isbn, String title, ArrayList<String> authors, String publisher, String publishDate, int numberOfCopies, int numberOfCheckedOut) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numberOfCopies = numberOfCopies;
        this.numberOfCheckedOut = numberOfCheckedOut;
    }
}
