package controller.command.undoableCommands;

import controller.command.Command;
import controller.command.UndoableCommand;
import model.BookwormLibrary;

import java.util.ArrayList;

/**
 * Borrow a book Command class
 */
public class BorrowBook implements UndoableCommand {

    private Long clientID;
    private Long visitorID;
    private ArrayList<String> bookIDs;

    public BorrowBook(String[] args) {
        clientID = Long.parseLong(args[0]);
        visitorID = Long.parseLong(args[2]);
        bookIDs = new ArrayList<>();
        for(int i = 3; i < args.length; i++){
            bookIDs.add(args[i]);
        }
    }

    @Override
    public void execute() {
        String response = BookwormLibrary.getInstance().checkOut(visitorID, bookIDs);
        if(response.equals("max-checkout-exceeded")){
            System.out.println(clientID + ",borrow,book-limit-exceeded;");
        }else{
            System.out.println(clientID + ",borrow," + response + ";");
        }
    }

    @Override
    public void undo() {
        BookwormLibrary.getInstance().checkIn(visitorID, bookIDs);
    }

    @Override
    public void redo() {
        execute();
    }
}