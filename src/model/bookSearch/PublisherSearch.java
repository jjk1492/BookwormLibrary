package model.bookSearch;

import model.Book;

import java.util.ArrayList;

public class PublisherSearch implements Query {

    private String publisher;
    private ArrayList<Book> matchingBooks;

    public PublisherSearch(String publisher) {
        this.publisher = publisher;
        this.matchingBooks = new ArrayList<>();
    }

    @Override
    public void query(Book book) {
        if( this.publisher.equals("*")){
            matchingBooks.add(book);
        }else {
            if( book.getPublisher().equals(this.publisher)){
                matchingBooks.add(book);
            }
        }
    }

    public ArrayList<Book> getMatchingBooks() {
        return matchingBooks;
    }
}
