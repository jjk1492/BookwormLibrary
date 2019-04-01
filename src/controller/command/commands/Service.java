package controller.command.commands;

import controller.command.Command;
import model.BookStore;
import model.BookwormLibrary;


public class Service implements Command {

    //client ID,service,info-service;
    private String[] commandArgs;

    public Service(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }

    @Override
    public void execute() {
        String service = this.commandArgs[2];

        if( service.equalsIgnoreCase("local")){
            BookwormLibrary.getInstance().setCatalogue(BookStore.getInstance().loadLocal());
        }else{
            BookwormLibrary.getInstance().setCatalogue(BookStore.getInstance().loadAPI());
        }

    }
}
