package controller.command.commands;

import controller.QueryHandler;
import controller.command.Command;

public class Service implements Command {

    //client ID,service,info-service;
    private Long clientID;
    private String service;
    private boolean correctArgCount;

    public Service(String[] args) {
        if(args.length == 3){
            correctArgCount = true;
            clientID = Long.parseLong(args[0]);
            service = args[2];
        }else {
            correctArgCount = false;
        }
    }

    @Override
    public void execute() {
        if(correctArgCount){
            if(service.equals("google") || service.equals("local")){
                QueryHandler.getActiveClients().get(clientID).setService(service);
                System.out.println(clientID + ",service," + service + ",success;");
            }else {
                System.out.println(clientID + ",service," + service + ",unsuccessful;");
                System.out.println("Service can be either \"google\" or \"local\";");
            }
        }else {
            System.out.println(clientID + ",service," + service + ",unsuccessful;");
            System.out.println("Did you input the correct number of arguments?;");
        }
    }
}
