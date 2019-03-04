package commands;

import controller.BookwormLibrary;

/**
 * Abstract class that all commands will inherit from
 */
public abstract class command {

    public BookwormLibrary library = BookwormLibrary.getInstance();

    public abstract String runCommand(String[] args);

}