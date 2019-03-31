package model.bookSearch;

import model.Book;

import java.util.ArrayList;

public class TitleSearch implements Query {

    private ArrayList<Book> filteredResults;
    private String title;

    public TitleSearch(String title){
        this.title = title;
        this.filteredResults = new ArrayList<>();
    }

    @Override
    public void query(Book book) {
        if( this.title.equals("*")){
            filteredResults.add(book);
        } else if( book.getTitle().equals(this.title)){
            filteredResults.add(book);
        }
    }

    public ArrayList<Book> getFilteredResults() {
        return filteredResults;
    }
}
