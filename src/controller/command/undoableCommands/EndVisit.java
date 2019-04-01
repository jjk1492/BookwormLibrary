package controller.command.undoableCommands;

import controller.command.Command;
import controller.command.Undoable;
import model.BookwormLibrary;
import model.Visit;

/**
 * End a visit Command
 */
public class EndVisit implements Undoable, Command {

    private Long clientID;
    private Long visitorID;
    private boolean correct;

    public EndVisit(String[] args) {
        if(args.length == 3){
            clientID = Long.parseLong(args[0]);
            visitorID = Long.parseLong(args[2]);
            correct = true;
        }else {
            correct = false;
            System.out.println(args[0] + ",depart,invalid-id;");
        }
    }

    @Override
    public void execute() {
        if(correct){
            Visit v = BookwormLibrary.getInstance().removeFromVisits(visitorID);
            System.out.println(clientID + "," + v.getEndTime() + "," + v.getVisitTimeMinutes() + ";");
        }
    }

    @Override
    public void undo() {
        if(correct) {
            BookwormLibrary.getInstance().addToVisits(visitorID);
            System.out.println(clientID + ",undo,success;");
        }
    }

    @Override
    public void redo() {
        execute();
    }

    /*@Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            return "depart" + this.library.removeFromVisits(args[1]) + ';';
        }
        return "depart,missing-parameters,{visitor ID};";
    }*/

}