package controller.command.undoableCommands;

import controller.command.UndoableCommand;
import model.BookwormLibrary;

import java.util.ArrayList;

/**
 * Return a book to the library Command
 */
public class ReturnBook implements UndoableCommand {

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

    }



    /*@Override
    public String runCommand(String[] args) {
        if (args.length >= 3) {
            ArrayList < String > ids = new ArrayList < > ();
            for (int i = 2; i <= args.length - 1; i++) {
                ids.add(args[i]);
            }

            return "return," + library.checkIn(args[1], ids) + ";";
        } else {
            return "return,missing-parameters,{visitorID,id[,id]};";
        }
    }*/
}