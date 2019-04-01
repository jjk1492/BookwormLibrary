package controller.command.undoableCommands;

import controller.command.Command;
import controller.command.UndoableCommand;
import model.BookwormLibrary;
import model.Visit;
import model.Visitor;

/**
 * Begin visit Command
 */
public class BeginVisit implements UndoableCommand {

    private Long clientID;
    private Long visitorID;
    private boolean correct;

    public BeginVisit(String[] args) {
        if(args.length == 3){
            clientID = Long.parseLong(args[0]);
            visitorID = Long.parseLong(args[2]);
            correct = true;
        }else {
            correct = false;
            System.out.println(args[0] + ",arrive,invalid-id;");
        }
    }

    @Override
    public void execute() {
        if(correct){
            Visitor vis = BookwormLibrary.getInstance().getVisitors().get(visitorID);
            if(vis == null){
                System.out.println(clientID + ",arrive,invalid-id;");
                return;
            }
            for(Visit v : BookwormLibrary.getInstance().getCurrentVisits()){
                if(v.getVisitorId().equals(visitorID)){
                    System.out.println(clientID + ",arrive,duplicate;");
                    return;
                }
            }
            Visit visit = BookwormLibrary.getInstance().addToVisits(visitorID);
            System.out.println(clientID + ",arrive," + visitorID + "," + visit.getStartDate() + "," + visit.getStartTime() + ";");
        }
    }

    @Override
    public void undo() {
        if(correct) {
            BookwormLibrary.getInstance().removeFromVisits(visitorID);
            System.out.println(clientID + ",undo,success;");
        }
    }

    @Override
    public void redo() {
        execute();
    }
}