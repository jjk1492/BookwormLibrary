package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;
import model.Visitor;

import java.time.format.DateTimeFormatter;

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

    @Override
    public void execute() {
        if(correctArgCount){
            if(BookwormLibrary.getInstance().verifyUser(firstName,lastName,address,phoneNumber)){
                Visitor visitor = BookwormLibrary.getInstance().registerUser(firstName,lastName,address,phoneNumber);
                System.out.println(clientid + "," + visitor.getUserID() + "," + visitor.getTimeOfCreation().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + ";");
            }else{
                System.out.println(clientid + ",register,duplicate;");
            }
        }else {
            System.out.println(clientid + ",register,missing-parameters,{first name,last name,address,phone-number};");
            System.out.println("Did you input the correct number of arguments?;");
        }
    }
}