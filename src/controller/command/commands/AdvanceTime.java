package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;

/**
 * Advance time Command
 */
public class AdvanceTime implements Command {

    private long clientID;
    private int days;
    private int hours;
    private boolean correct;

    public AdvanceTime(String[] args) {
        clientID = Long.parseLong(args[0]);
        hours = -1;
        correct = true;
        if(args.length == 3){                               //only advancing by a number of days
            if(args[2].matches("[0-9]+")){              //check to ensure days is a number
                days = Integer.parseInt(args[2]);           //Set days to args[2]
            }else{
                System.out.println(clientID + ",advance,invalid-number-of-days," + args[2] + ";");      //out put error message
                correct = false;
            }
        }else if(args.length == 4){                         //both days and hours
            if(args[2].matches("[0-9]+")){              //check to ensure days is a number
                days = Integer.parseInt(args[2]);           //Set days to args[2]
            }else{
                System.out.println(clientID + ",advance,invalid-number-of-days," + args[2] + ";");      //out put error message
                correct = false;
            }
            if(args[3].matches("[0-9]+")){              //check to ensure days is a number
                days = Integer.parseInt(args[2]);           //Set days to args[2]
            }else{
                System.out.println(clientID + ",advance,invalid-number-of-hours," + args[3] + ";");      //out put error message
                correct = false;
            }
        }else {
            System.out.println(clientID + ",advance,unsuccessful;");
            System.out.println("wrong number of arguments;");
        }
    }

    @Override
    public void execute() {
        if(correct) {
            BookwormLibrary.getInstance().advanceTimeDays(days);
            if (hours != -1) {
                BookwormLibrary.getInstance().advanceTimeHours(hours);
            }
            System.out.println(clientID + ",advance,success;");
        }
    }
}