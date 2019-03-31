package model;

import model.bookSearch.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BookStore {

    private static BookStore INSTANCE;
    private ArrayList<Book> catalogue;

    public BookStore(){
        if(INSTANCE == null) {
            INSTANCE = this;
            this.catalogue = loadBooks();
        }
    }

    private ArrayList<Book> loadBooks(){

        ArrayList < Book > books = new ArrayList < > ();

        //Load in the supplied books from the books.txt files
        try {
            Scanner scanner = new Scanner(new File("./src/books.txt"));

            while (scanner.hasNextLine()) {
                ArrayList < String > vals = new ArrayList < > ();
                StringBuilder builder = new StringBuilder();

                boolean inSpecial = false;
                for (char c: scanner.nextLine().toCharArray()) {
                    if (c == '"' || c == '{' || c == '}') {
                        inSpecial = !inSpecial;
                    } else if (c == ',' && !inSpecial) {
                        vals.add(builder.toString());
                        builder = new StringBuilder();
                    } else {
                        builder.append(c);
                    }
                }

                vals.add(builder.toString());

                if (vals.get(4).length() == 4) {
                    vals.set(4, vals.get(4) + "-01-01");
                } else if (vals.get(4).length() == 7) {
                    vals.set(4, vals.get(4) + "-01");
                }

                books.add(new Book(vals.get(0), vals.get(1), new ArrayList < > (Arrays.asList(vals.get(2).split(","))), vals.get(3), new SimpleDateFormat("yyyy-MM-dd").parse(vals.get(4)), Integer.parseInt(vals.get(5))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TO DO: Load in books form the Google Books API

        return books;
    }

    public ArrayList<Book> titleSearch(String title, ArrayList<Book> toFilter){
        TitleSearch matchingBooks = new TitleSearch(title);
        if( toFilter.isEmpty()){
            toFilter = this.catalogue;
        }
        if( title.equals("*")){
            return toFilter;
        }
        for(Queryable q: toFilter){
            q.doQuery(matchingBooks);
        }
        return  matchingBooks.getFilteredResults();
    }

    public ArrayList<Book> authorSearch(ArrayList<String> authors, ArrayList<Book> toFilter){
        AuthorSearch matchingBooks = new AuthorSearch(authors);
        if( toFilter.isEmpty() ){
            toFilter = this.catalogue;
        }
        if( authors.get(0).equals("*")){
            return toFilter;
        }
        for( Queryable q : toFilter){
            q.doQuery(matchingBooks);
        }
        return matchingBooks.getMatchingBooks();
    }

    public ArrayList<Book> publisherSearch(String publisher, ArrayList<Book> toFilter){
        PublisherSearch matchingBooks = new PublisherSearch(publisher);
        if( toFilter.isEmpty() ){
            toFilter = this.catalogue;
        }
        if( publisher.equals("*")){
            return toFilter;
        }
        for( Queryable q : toFilter){
            q.doQuery(matchingBooks);
        }
        return matchingBooks.getMatchingBooks();
    }

    public ArrayList<Book> isbnSearch(String isbn, ArrayList<Book> toFilter){
        isbnSearch matchingBooks = new isbnSearch(isbn);
        if( toFilter.isEmpty() ){
            toFilter = this.catalogue;
        }
        if( isbn.equals("*")){
            return toFilter;
        }
        for( Queryable q : toFilter){
            q.doQuery(matchingBooks);
        }
        return matchingBooks.getMatchingBooks();
    }


    public static BookStore getInstance() {
        return INSTANCE;
    }
}
