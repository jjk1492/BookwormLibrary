package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;

/**
 * Purchase a book from catalogue Command
 */
public class BookPurchase implements Command {

    private String[] commandArgs;

    public BookPurchase(String[] commandargs) {
        this.commandArgs = commandargs;
    }

    @Override
    public void execute() {
        int quantity = Integer.parseInt(this.commandArgs[2]);
        BookwormLibrary library = BookwormLibrary.getInstance();
        for( int i=3; i<this.commandArgs.length; i++){
            library.buyBook(this.commandArgs[i], quantity);
        }
    }
}