package controller.command.undoableCommands;

import controller.command.Command;
import controller.command.Undoable;
import model.BookwormLibrary;

import java.util.ArrayList;

/**
 * Return a book to the library Command
 */
public class ReturnBook implements Undoable, Command {

    private Long clientID;
    private Long visitorID;
    private ArrayList<String> bookIDs;

    public ReturnBook(String[] args) {
        clientID = Long.parseLong(args[0]);
        visitorID = Long.parseLong(args[2]);
        bookIDs = new ArrayList<>();
        for(int i = 3; i < args.length; i++){
            bookIDs.add(args[i]);
        }
    }

    @Override
    public void execute() {
        String response = BookwormLibrary.getInstance().checkIn(visitorID, bookIDs);
        System.out.println(clientID + ",borrow," + response);
    }

    @Override
    public void undo() {
        BookwormLibrary.getInstance().checkOut(visitorID, bookIDs);
    }

    @Override
    public void redo() {
        BookwormLibrary.getInstance().checkIn(visitorID, bookIDs);
    }
}