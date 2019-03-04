package commands;

import java.util.ArrayList;

public class returnBook extends command {
    @Override
    public String runCommand(String[] args) {
        if (args.length >= 3) {
            ArrayList < String > ids = new ArrayList < > ();
            for (int i = 2; i <= args.length - 1; i++) {
                ids.add(args[i]);
            }

            return "return," + library.checkIn(args[1], ids) + ";";
        } else {
            return "return,missing-parameters,{visitorID,id[,id]};";
        }
    }
}