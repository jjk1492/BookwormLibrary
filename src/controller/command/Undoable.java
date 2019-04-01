package controller.command;

/**
 * Simple interface the undoable commands interface
 */
public interface Undoable {


    /**
     * Undo part of the Command
     * Handles when the user call undo from the Command line
     */
    public void undo();

    /**
     * Redo part of the Command
     * Handle when the user calls redo from the Command line
     */
    public void redo();
}
