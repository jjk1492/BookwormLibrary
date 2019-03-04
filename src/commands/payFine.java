package commands;

import model.Visitor;

public class payFine extends command {
    @Override
    public String runCommand(String[] args) {
        if (args.length == 3) {
            Visitor v = library.getVisitors().get(args[1]);
            if (v == null) {
                return "pay,invalid-user-id;";
            } else {
                double d = Double.parseDouble(args[2]);
                if (d <= 0 || d > v.getFine()) {
                    return "pay,invalid-amount," + d + "," + v.getFine() + ";";
                }
                v.payFine(d);
                return "pay,success," + v.getFine() + ";";
            }
        } else {
            return "pay,missing-parameters,{visitorID,amount};";
        }
    }
}