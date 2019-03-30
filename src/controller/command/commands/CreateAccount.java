package controller.command.commands;

import controller.command.Command;

public class CreateAccount implements Command {

    //client ID,create,username,password,role,visitor ID;
    private Long clientID;
    private String username;
    private String password;
    private String role;
    private Long visitorID;

    private boolean correctArgCount;

    public CreateAccount(String[] args) {
        if(args.length == 6){
            correctArgCount = true;
            clientID = Long.parseLong(args[0]);
            username = args[2];
            password = args[3];
            role = args[4];
            visitorID = Long.parseLong(args[5]);
        }else{
            correctArgCount = false;
        }
    }

    @Override
    public void execute() {
        if(correctArgCount){

        }else {
            System.out.println(clientID + ",create,unsuccessful;");
            System.out.print("Do you have the correct number of arguments?;");
        }
    }
}
