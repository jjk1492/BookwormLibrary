package model.bookSearch;

import model.Book;

import java.util.ArrayList;

public class isbnSearch implements Query {

    private String isbn;
    private ArrayList<Book> matchingBooks;

    public isbnSearch(String isbn) {
        this.isbn = isbn;
        this.matchingBooks = new ArrayList<>();
    }

    @Override
    public void query(Book book) {
        if( this.isbn .equals("*")){
            matchingBooks.add(book);
        }
        if( book.getISBN().equals(this.isbn)){
            matchingBooks.add(book);
        }
    }

    public ArrayList<Book> getMatchingBooks() {
        return matchingBooks;
    }
}
