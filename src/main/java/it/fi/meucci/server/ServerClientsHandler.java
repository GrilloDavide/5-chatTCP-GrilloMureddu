package it.fi.meucci.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;
import it.fi.meucci.MapHandler;

public class ServerClientsHandler {
    
    public static ArrayList <ServerClientThread> connectedClients;
    static HashMap <String, String> idNamesMap; // {id ; clientname}
    static int progressiveId;

    public ServerClientsHandler(){
        connectedClients = new ArrayList<>();
        idNamesMap = new HashMap<>();
        progressiveId = 0;
    }

    public static void addClient(ServerClientThread client){
        idNamesMap.put(assignId(), client.clientName);
        connectedClients.add(client);

    }

    public static void removeClient(){

    }

    private static String assignId(){
        String id = String.format("%05d", progressiveId);
        progressiveId++;
        return id;
    }

    public static String sendClientsMap(){
        return MapHandler.hashMapToString(idNamesMap);
    }



}