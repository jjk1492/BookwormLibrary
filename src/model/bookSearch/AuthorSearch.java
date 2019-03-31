package model.bookSearch;

import model.Book;

import java.util.ArrayList;

public class AuthorSearch implements Query{
    private ArrayList<String> authors;
    private ArrayList<Book> matchingBooks;

    public AuthorSearch(ArrayList<String> authors){
        this.authors = authors;
        this.matchingBooks = new ArrayList<>();
    }

    @Override
    public void query(Book book) {
        if( this.authors.get(0).equals("*")){
            matchingBooks.add(book);
        }else {
            for( String author : book.getAuthors() ){
                if (this.authors.contains(author)){
                    matchingBooks.add(book);
                    break;
                }
            }
        }
    }

    public ArrayList<Book> getMatchingBooks() {
        return matchingBooks;
    }
}
