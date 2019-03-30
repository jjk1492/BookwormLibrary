package controller;

import controller.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that interacts with the "server" (library)
 * Handles parsing of controller.command
 */
public class Client {

    private Invoker invoker;
    private Long clientID;
    private String service;

    private static List<Long> usedClientIDs;

    Client(){
        if(usedClientIDs == null){
            usedClientIDs = new ArrayList<>();
        }
        clientID = getUnusedClientID();
    }

    public Long getClientID(){
        return clientID;
    }

    private static Long getUnusedClientID(){
        Random r = new Random();
        while (true){
            long id = (long)(r.nextDouble() * 8999999999L) + 1000000000;
            if(!usedClientIDs.contains(id)){
                usedClientIDs.add(id);
                return id;
            }
        }
    }

    public void execute(UndoableCommand cm){
        invoker.execute(cm);
    }

    public void undo(){
        invoker.undo();
    }

    public void redo(){
        invoker.redo();
    }

    public void dispose(){
        invoker = null;
        clientID = null;
    }

    public static void removeClientID(Long id){
        if(usedClientIDs.contains(id)){
            usedClientIDs.remove(id);
        }
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}