package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;
import model.Visitor;

/**
 * Register a user to the library Command
 */
public class RegisterVisitor implements Command {

    //clientID,register,first name,last name,address, phone-number;
    private Long clientid;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean correctArgCount;

    public RegisterVisitor(String[] args) {
        clientid = Long.parseLong(args[0]);
        if(args.length == 6){
            correctArgCount = true;
            firstName = args[2];
            lastName = args[3];
            address = args[4];
            phoneNumber = args[5];
        }else {
            correctArgCount = false;
        }
    }

    //TODO: fix the registration date so it actually prints out a date
    @Override
    public void execute() {
        if(correctArgCount){
            if(BookwormLibrary.getInstance().verifyUser(firstName,lastName,address,phoneNumber)){
                Visitor visitor = BookwormLibrary.getInstance().registerUser(firstName,lastName,address,phoneNumber);
                System.out.println(clientid + "," + visitor.getUserID() + ",registration date;");
            }else{
                System.out.println(clientid + ",register,duplicate;");
            }
        }else {
            System.out.println(clientid + ",register,missing-parameters,{first name,last name,address,phone-number};");
            System.out.println("Did you input the correct number of arguments?;");
        }
    }

    /*@Override
    public String runCommand(String[] args) {
        if (args.length == 5) {
            return "register," + library.registerUser(args[1], args[2], args[3], args[4]) + ";";
        } else {
            return "register,missing-parameters,{first name,last name,address, phone-number};";
        }
    }*/
}