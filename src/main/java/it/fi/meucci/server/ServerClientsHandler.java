package it.fi.meucci.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Map.Entry;
import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;

public class ServerClientsHandler {
    
    public static ArrayList <ServerClientThread> connectedClients;
    static LinkedHashMap<String, String> idNamesMap; // {id ; clientname}
    static int progressiveId;

    public ServerClientsHandler(){
        connectedClients = new ArrayList<>();
        idNamesMap = new LinkedHashMap<>();
        progressiveId = 0;
    }

    public static void addClient(ServerClientThread client){
        idNamesMap.put(assignId(), client.clientName);
        connectedClients.add(client);
        updateClientsMaps();
        System.out.println(idNamesMap);
    }

    public static void removeClient(ServerClientThread clientThread){

        connectedClients.remove(clientThread);
        idNamesMap.remove(MapHandler.getIdByName(clientThread.clientName,idNamesMap));
        updateClientsMaps();
        clientThread.interrupt();

    }

    public static void updateClientsMaps(){
        connectedClients.forEach(a ->{
            try {
                a.messageToClient(Prefix.LST+MapHandler.hashMapToString(idNamesMap));
            } catch (IOException e) {

            }
        });
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