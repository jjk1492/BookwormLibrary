package controller.command.commands;

import controller.command.Command;

/**
 * Find borrowed books from a user Command
 */
public class FindBorrowBook implements Command {

    private String[] commandArgs;

    public FindBorrowBook(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }

    @Override
    public void execute() {

    }

}