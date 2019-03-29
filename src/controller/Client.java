package controller;

import com.oracle.tools.packager.Log;
import controller.command.*;
import controller.command.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class that interacts with the "server" (library)
 * Handles parsing of controller.command
 */
public class Client {

    private Invoker invoker;
    private Long clientID;

    public static List<Long> usedClientIDs;

    public Client(){
        if(usedClientIDs == null){
            usedClientIDs = new ArrayList<>();
        }
        clientID = getUnusedClientID();
    }

    public Long getClientID(){
        return clientID;
    }

    private Long getUnusedClientID(){
        Random r = new Random();
        while (true){
            long id = (long)(r.nextDouble() * 8999999999L) + 1000000000;
            if(!usedClientIDs.contains(id)){
                usedClientIDs.add(id);
                return id;
            }
        }
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
}