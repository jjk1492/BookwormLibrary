package commands;

public class beginVisit extends command {

    @Override
    public String runCommand(String[] args) {
        if (args.length == 2) {
            return "arrive," + this.library.addToVisits(args[1]) + ';';
        } else {
            return "arrive,missing-parameters,{visitor ID};";
        }
    }
}