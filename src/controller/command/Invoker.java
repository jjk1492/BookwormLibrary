package controller.command;

import java.util.Stack;

/**
 * Invoke Class to handle the undo and redo stacks
 */
public class Invoker {

    /**
     * The undo stack that handles the controller.command to undo
     */
    private Stack<Command> undoStack;

    /**
     * The redo stack that handles the controller.command to redo
     */
    private Stack<Command> redoStack;

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
    public void execute(Command cm){
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
            Command cm = undoStack.pop();
            cm.undo();
            redoStack.push(cm);
        }
    }

    /**
     * Redo function
     * Redos the recent command from the stack
     */
    public void redo(){
        Command cm = redoStack.pop();
        cm.execute();
        undoStack.push(cm);
    }
}
