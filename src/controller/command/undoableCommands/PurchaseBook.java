package controller.command.undoableCommands;

import controller.command.UndoableCommand;
import model.Book;
import model.BookwormLibrary;

public class PurchaseBook implements UndoableCommand {

    private int quantity;
    private String[] bookIds;
    private BookwormLibrary library = BookwormLibrary.getInstance();

    public PurchaseBook(String[] commandArgs) {
        this.quantity = Integer.parseInt(commandArgs[2]);
        int numbBooks = commandArgs.length - 3;
        bookIds = new String[numbBooks];
        for( int i=3; i<numbBooks; i++){
            bookIds[i=3] = commandArgs[i];
        }
    }

    @Override
    public void execute() {
        System.out.println("buy,success," + this.bookIds.length + "\n");
        for( String isbn : this.bookIds){
            library.buyBook(isbn, this.quantity);
            Book b = library.getBooks().get(isbn);
            System.out.println(b.getISBN() + "," + b.getTitle() + "," + b.getAuthorsPretty() + "," +  b.getPublishDateAsString() + "," + this.quantity);
        }
    }

    @Override
    public void undo() {
        for( String string : this.bookIds){
            library.removeBook(string, this.quantity);
        }
    }

    @Override
    public void redo() {
        for( String isbn : this.bookIds){
            library.buyBook(isbn, this.quantity);
        }
    }
}
