package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;
import model.Visitor;

public class CreateAccount implements Command {

    //client ID,create,username,password,role,visitor ID;
    private Long clientID;
    private String username;
    private String password;
    private String role;
    private Long visitorID;

    private boolean correctArgCount;

    public CreateAccount(String[] args) {
        clientID = Long.parseLong(args[0]);
        if(args.length == 6){
            correctArgCount = true;
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
            for(Visitor v : BookwormLibrary.getInstance().getVisitors().values()){
                if(v.getUsername() != null && v.getUsername().equals(username)){
                    System.out.println(clientID + ",create,duplicate-username;");
                    return;
                }
            }
            Visitor target = BookwormLibrary.getInstance().getVisitors().get(visitorID);
            if(target == null){
                System.out.println(clientID + ",create,invalid-visitor;");
            }else{
                if(target.hasAccount()){
                    System.out.println(clientID + ",create,duplicate-visitor;");
                }else{
                    if(role.equals("employee")) {
                        target.updateAccount(username, password, true);
                        System.out.println(clientID + ",create,success;");
                    }else if(role.equals("visitor")){
                        target.updateAccount(username, password, false);
                        System.out.println(clientID + ",create,success;");
                    }else{
                        System.out.println(clientID + ",create,invalid-role;");
                    }
                }
            }
        }else {
            System.out.println(clientID + ",create,unsuccessful;");
            System.out.println("Do you have the correct number of arguments?;");
        }
    }
}
