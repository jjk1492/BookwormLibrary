package commands;

import controller.BookwormLibrary;
import model.Visit;
import model.Visitor;

import java.util.ArrayList;

public class beginVisit extends command{

    @Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            Visitor v = BookwormLibrary.getInstance().getVisitors().get(args[1]);
            if (v == null) {
                return "arrive,invalid-id;";
            }
            ArrayList<Visit> temp = BookwormLibrary.getInstance().getCurrentVisits();
            for (Visit vis : temp) {
                if (vis.getVisitorId() == v.getUserID()) {
                    return "arrive,duplicate;";
                }
            }
            Visit visit = new Visit(BookwormLibrary.getInstance().getVisitors().get(args[1]));
            BookwormLibrary.getInstance().addToVisits(visit);
            return "arrive," + v.getUserID() + "," + visit.getStartDate() + "," + visit.getStartTime() + ";";
        }
        else{
            return "arrive,missing-parameters,{visitor ID};";
        }
    }
}
