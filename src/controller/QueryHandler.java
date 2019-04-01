package controller;

import controller.command.Command;
import controller.command.UndoableCommand;
import controller.command.commands.*;
import controller.command.undoableCommands.*;
import model.Visitor;

import java.util.HashMap;
import java.util.Map;

public class QueryHandler {

    private static Map<Long, Client> activeClients; //

    QueryHandler(){
        activeClients = new HashMap<>();
    }

    public static Map<Long, Client> getActiveClients() {
        return activeClients;
    }

    public boolean handleQuery(String query){    //remove spaces from input
        String[] args = query.split(",");                         //split the string on commas

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

        //TODO:Fix this to the proper format (if there is one) including safe shutdown
        if(args.length == 1 && args[0].equals("shutdown")){
            Command cm = new shutdown();
            cm.execute();
            System.out.println("shutting-down;");
            return false;
        }

        //Check to make sure the first argument is numeric
        Client activeClient;
        if (args[0].matches("[0-9]+") && args[0].length() > 2){
            //Gets the client that called the command
            if(activeClients.keySet().contains(Long.parseLong(args[0]))){
                activeClient = activeClients.get(Long.parseLong(args[0]));
            }else {
                System.out.println("invalid-client-id;");
                return true;
            }
        }else{
            System.out.println("bad-client-id;");
            return true;
        }

        //Switch on the first item in args which should be the command
        Command cm;
        UndoableCommand ucm;
        switch (args[1]){
            case "advance":                                         //Client wants to advance the time
                cm = new AdvanceTime(args);
                cm.execute();
                return true;
            case "arrive":                                          //Client wants to begin a visit to the library
                ucm = new BeginVisit(args);
                activeClient.execute(ucm);
                return true;
            case "borrow":                                          //Client wants to borrow a book
                ucm = new BorrowBook(args);
                activeClient.execute(ucm);
                return true;
            case "borrowed":                                        //Client wants to find a book that is borrowed
                cm = new FindBorrowBook(args);
                cm.execute();
                return true;
            case "buy":                                             //Client wants to buy a book from the service
                ucm = new PurchaseBook(args);
                activeClient.execute(ucm);
                return true;
            case "create":                                          //Client wants to create a new account
                cm = new CreateAccount(args);
                cm.execute();
                return true;
            case "datetime":                                        //Client wants a report on current time
                cm = new CurrentTime(args);
                cm.execute();
                return true;
            case "depart":                                          //Client wants to end a visit to the library
                ucm = new EndVisit(args);
                activeClient.execute(ucm);
                return true;
            case "disconnect":                                      //disconnect the client based of client id
                System.out.println(activeClient.getClientID() + ",disconnect;");
                activeClients.remove(activeClient.getClientID());
                Client.removeClientID(activeClient.getClientID());
                activeClient.dispose();
                return true;
            case "info":
                //Client wants to search for a book in the library
                cm = new LibraryBookSearch(args);
                cm.execute();
                return true;
            case "login":                                           //Client wants to login into a account
                cm = new LogIn(args);
                cm.execute();
                return true;
            case "logout":                                          //Client wants to logout of account
                cm = new LogOut(args);
                cm.execute();
                return true;
            case "pay":                                             //Client wants to pay there outstanding fines
                ucm = new PayFine(args);
                activeClient.execute(ucm);
                return true;
            case "redo":                                            //Client wants to redo most recent undone command
                activeClient.redo();
                return true;
            case "register":                                        //Client wants to register a account
                cm = new RegisterVisitor(args);
                cm.execute();
                return true;
            case "report":                                          //Client wants a report on the library
                cm = new Report(args);
                cm.execute();
                return true;
            case "return":                                          //Client wants to return a book
                ucm = new ReturnBook(args);
                activeClient.execute(ucm);
                return true;
            case "search":                                          //Client wants to search the book store
                cm = new BookStoreSearch(args);
                cm.execute();
                return true;
            case "service":                                         //Client wants to set the service they are using
                cm = new Service(args);
                cm.execute();
                return true;
            case "undo":                                            //Client wants to undo most recent undoable command
                activeClient.undo();
                return true;
            default:                                                //Client input a unknown command
                System.out.println("unknown-command;");
                return true;
        }
    }
}
