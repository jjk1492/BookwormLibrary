package controller.command.commands;

import controller.QueryHandler;
import controller.command.Command;
import model.BookwormLibrary;
import model.Visitor;

public class LogIn implements Command {

    private Long clientID;
    private String username;
    private String password;
    private boolean correct;

    public LogIn(String[] args) {
        if(args.length == 4){
            correct = true;
            clientID = Long.parseLong(args[0]);
            username = args[2];
            password = args[3];
        }else {
            correct = false;
            System.out.println(args[0] + ",login,unsuccessful;");
        }
    }

    @Override
    public void execute() {
        if(correct){
            for(Visitor v : BookwormLibrary.getInstance().getVisitors().values()){
                if(v.getUsername().equals(username) && v.checkPassword(password)){
                    QueryHandler.getActiveClients().get(clientID).setVisitor(v);
                    System.out.println(clientID + ",login,success;");
                    return;
                }
            }
        }
        System.out.println(clientID + ",login,bad-username-or-password;");
    }
}
