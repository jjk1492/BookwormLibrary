package controller.command.commands;

import controller.command.Command;
import model.Visitor;

/**
 * Pay a user's fine Command
 */
public class PayFine implements Command {


    public PayFine() {
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
        if (args.length == 3) {
            Visitor v = library.getVisitors().get(args[1]);
            if (v == null) {
                return "pay,invalid-user-id;";
            } else {
                double d = Double.parseDouble(args[2]);
                if (d <= 0 || d > v.getFine()) {
                    return "pay,invalid-amount," + d + "," + v.getFine() + ";";
                }
                v.PayFine(d);
                return "pay,success," + v.getFine() + ";";
            }
        } else {
            return "pay,missing-parameters,{visitorID,amount};";
        }
    }*/
}