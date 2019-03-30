package controller.command.undoableCommands;

import controller.command.UndoableCommand;

/**
 * End a visit Command
 */
public class EndVisit implements UndoableCommand {

    public EndVisit(String[] args) {
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    /*@Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            return "depart" + this.library.removeFromVisits(args[1]) + ';';
        }
        return "depart,missing-parameters,{visitor ID};";
    }*/

}