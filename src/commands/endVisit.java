package commands;

/**
 * End a visit command
 */
public class endVisit extends command {

    @Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            return "depart" + this.library.removeFromVisits(args[1]) + ';';
        }
        return "depart,missing-parameters,{visitor ID};";
    }

}