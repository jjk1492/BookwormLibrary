package controller.command;

/**
 * Simple Interface that commands implement
 */
public interface Command {

    /**
     * Execute part of the Command
     * Handles when the user calls a Command from the Command line
     */
    public void execute();

}