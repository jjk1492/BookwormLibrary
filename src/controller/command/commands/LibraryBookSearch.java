package controller.command.commands;

import controller.command.Command;
import model.Book;
import model.BookStore;

import java.util.ArrayList;

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
    }
}