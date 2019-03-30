package controller.command.undoableCommands;

import controller.command.UndoableCommand;

/**
 * Return a book to the library Command
 */
public class ReturnBook implements UndoableCommand {
    public ReturnBook(String[] args) {
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

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