package controller.command.commands;

import controller.command.Command;

/**
 * End a visit Command
 */
public class EndVisit implements Command {

    public EndVisit() {
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