package controller.command.commands;

import controller.QueryHandler;
import controller.command.Command;

public class LogOut implements Command {

    private Long clientID;

    public LogOut(String[] args) {
        clientID = Long.parseLong(args[0]);
    }

    @Override
    public void execute() {
        QueryHandler.getActiveClients().get(clientID).setVisitor(null);
        System.out.println(clientID + ",logout,success;");
    }
}
