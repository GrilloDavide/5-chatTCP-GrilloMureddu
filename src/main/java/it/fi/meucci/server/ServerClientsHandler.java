package it.fi.meucci.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;

public class ServerClientsHandler {
    
    public static ArrayList <ServerClientThread> connectedClients = new ArrayList<>();
    static LinkedHashMap<String, String> idNamesMap = new LinkedHashMap<>(); // {id ; clientname}
    static int progressiveId = 0;

    public static boolean addClient(ServerClientThread client){
        
        for (Map.Entry<String, String> entry : idNamesMap.entrySet()){
            if(client.clientName.equals(entry.getValue())){
                
                return false;
            }

        }
        idNamesMap.put(assignId(), client.clientName);
        connectedClients.add(client);
        updateClientsMaps();
        return true;
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
