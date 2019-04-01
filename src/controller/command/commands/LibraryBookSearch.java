package controller.command.commands;

import controller.command.Command;
import model.Book;
import model.BookStore;
import model.BookwormLibrary;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Search the library for a book Command
 */
public class LibraryBookSearch implements Command {

    String[] commandArgs;

    public LibraryBookSearch(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }

    @Override
    public void execute() {
        ArrayList<Book> matchingBooks;
        String bookTitle = this.commandArgs[2];
        String authorsString = "*";
        String isbn = "*";
        String publisher = "*";
        String sortOrder = "*";

        BookwormLibrary library = BookwormLibrary.getInstance();
        ArrayList<Book> toFilter = new ArrayList<>(library.getBooks().values());

        try{
            int i = 3;
            authorsString = this.commandArgs[i++].replace("{", "") + ",";
            while( !authorsString.endsWith("}") ){
                authorsString = authorsString.concat(this.commandArgs[i++] + ",");
            }
            isbn = this.commandArgs[i++];
            publisher = this.commandArgs[i++];
            sortOrder = this.commandArgs[i];
        }catch (ArrayIndexOutOfBoundsException e){

        }

        authorsString = authorsString.replace("},","");
        authorsString = authorsString.replace("}","");

        BookStore bookStore = BookStore.getInstance();

        String[] authorsArray = authorsString.split(",");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsArray));

        matchingBooks = bookStore.titleSearch(bookTitle, toFilter);
        matchingBooks = bookStore.authorSearch(authors, matchingBooks);
        matchingBooks = bookStore.publisherSearch(publisher, matchingBooks);
        matchingBooks = bookStore.isbnSearch(isbn, matchingBooks);

        for(Book book: matchingBooks){
            System.out.println("Found book: " + book.getTitle() + " by the author: " + book.getAuthors());
        }
    }
}