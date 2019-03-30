package controller.command;

import java.util.Stack;

/**
 * Invoke Class to handle the undo and redo stacks
 */
public class Invoker {

    /**
     * The undo stack that handles the controller.command to undo
     */
    private Stack<UndoableCommand> undoStack;

    /**
     * The redo stack that handles the controller.command to redo
     */
    private Stack<UndoableCommand> redoStack;

    /**
     * Constructor for the Invoker
     * Creates new undo/redo stacks
     */
    public Invoker(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Execute function called from the command
     * Called from the class that creates the command
     * @param cm - the command that is being executed
     */
    public void execute(UndoableCommand cm){
        undoStack.push(cm);
        redoStack.clear();
        cm.execute();
    }

    /**
     * Undo function
     * Undos the recent command from the stack if the stack is not empty
     */
    public void undo(){
        if(!undoStack.empty()){
            UndoableCommand cm = undoStack.pop();
            cm.undo();
            redoStack.push(cm);
        }
    }

    /**
     * Redo function
     * Redos the recent command from the stack
     */
    public void redo(){
        UndoableCommand cm = redoStack.pop();
        cm.execute();
        undoStack.push(cm);
    }

    /**
     * reset function
     * resets the stacks when a command that cannot be undone is called
     */
    public void reset(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
}
