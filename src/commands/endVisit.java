package commands;

import controller.BookwormLibrary;
import model.Visit;
import model.Visitor;

import java.util.ArrayList;

public class endVisit extends command{

    @Override
    public String runCommand(String[] args) {
        ArrayList<Visit> v = BookwormLibrary.getInstance().getCurrentVisits();
        for (Visit vis : v){
            if (vis.getVisitorId().equals(args[1])){
                vis.endVisit();
                BookwormLibrary.getInstance().removeFromVisits(vis);
                return "depart," + vis.getVisitorId() + "," + vis.getEndTime() + "," + vis.getVisitTimeMinutes();
            }
        }
        return "arrive,invalid-id";
    }

}
