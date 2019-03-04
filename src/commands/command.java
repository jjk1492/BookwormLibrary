package commands;

import controller.BookwormLibrary;

public abstract class command {

    public BookwormLibrary library = BookwormLibrary.getInstance();

    public abstract String runCommand(String[] args);

}
