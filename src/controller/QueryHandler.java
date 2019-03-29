package controller;

import java.util.HashMap;
import java.util.Map;

public class QueryHandler {

    private Map<Long, Client> activeClients;

    public QueryHandler(){
        activeClients = new HashMap<>();
    }

    public boolean handleQuery(String query){
        String s1 = query.replaceAll("\\s", "");    //remove spaces from input
        String[] args = s1.split(",");                         //split the string on commas

        //Check to ensure the command ends with a ';'
        if (!args[args.length - 1].endsWith(";")) {
            System.out.println("partial-request;");
            return true;
        }

        //Remove ";" from end of the last string
        args[args.length - 1] = args[args.length - 1].substring(0, args[args.length - 1].length() - 1);

        //Check to see if the first argument is a connect command
        if(args.length == 1 && args[0].equals("connect")){
            Client newClient = new Client();
            activeClients.put(newClient.getClientID(), newClient);
            System.out.println("connect," + newClient.getClientID() + ";");
            return true;
        }

        //TODO:Fix this to the proper format
        if(args.length == 1 && args[0].equals("shutdown")){
            System.out.println("Shutting down");
            return false;
        }

        //Gets the client that called the command
        Client activeClient;
        if(activeClients.keySet().contains(Long.parseLong(args[0]))){
            activeClient = activeClients.get(Long.parseLong(args[0]));
        }else {
            System.out.println("invalid-client-id;");
            return true;
        }

        //Switch on the first item in args which should be the command to connect/disconnect/switch service
        switch (args[1]){
            case "disconnect":                                      //disconnect the client based of client id
                System.out.println(activeClient.getClientID() + ",disconnect;");
                activeClients.remove(activeClient.getClientID());
                Client.removeClientID(activeClient.getClientID());
                activeClient.dispose();
                return true;
        }

        return true;
    }
}
