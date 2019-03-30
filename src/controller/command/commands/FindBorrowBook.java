package controller.command.commands;

import controller.command.Command;
import model.CheckOut;

/**
 * Find borrowed books from a user Command
 */
public class FindBorrowBook implements Command {

    public FindBorrowBook(String[] args) {
    }

    @Override
    public void execute() {

    }

    /*@Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            if (library.getCheckedOutBooks().containsKey(args[1])) {
                int id = 0;
                StringBuilder sb = new StringBuilder();
                sb.append(library.getCheckedOutBooks().get(args[1]).size());

                for (CheckOut c: library.getCheckedOutBooks().get(args[1])) {
                    sb.append(",\n");
                    sb.append(++id);
                    sb.append(",");
                    sb.append(c.getBook().getISBN());
                    sb.append(",");
                    sb.append(c.getBook().getTitle());
                    sb.append(",");
                    sb.append(c.getCheckedDate());
                }

                return "borrow," + sb.toString() + ";";
            }
            return "borrow,invalid-visitor-id;";
        } else {
            return "borrowed,missing-parameters,{visitor ID};";
        }
    }*/
}