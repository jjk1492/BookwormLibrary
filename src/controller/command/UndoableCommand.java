package controller.command;

/**
 * Simple interface the undoable commands interface
 */
public interface UndoableCommand {

    /**
     * Execute part of the Command
     * Handles when the user calls a Command from the Command line
     */
    public void execute();

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
