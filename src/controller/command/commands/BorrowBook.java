package controller.command.commands;

import controller.command.Command;

/**
 * Borrow a book Command class
 */
public class BorrowBook implements Command {
    public BorrowBook() {
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
                if (i == 2 && i == args.length - 1) {
                    //only one book in the list
                    String s = args[i];
                    s = s.replace("{", "");
                    s = s.replace("}", "");
                    ids.add(s);
                } else if (i == 2) {
                    //first book in the list
                    //remove "{"
                    String s = args[i];
                    s = s.replace("{", "");
                    ids.add(s);
                } else if (i == args.length - 1) {
                    //last book in the list
                    //remove "}"
                    String s = args[i];
                    s = s.replace("}", "");
                    ids.add(s);
                } else {
                    //and ID
                    ids.add(args[i]);
                }
            }

            return "borrow," + library.checkOut(args[1], ids) + ";";
        }
        return "borrow,missing-parameters,{visitorID,{id}};";
    }*/
}