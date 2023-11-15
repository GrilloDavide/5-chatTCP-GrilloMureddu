package it.fi.meucci.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;

public class ServerClientsHandler {
    
    ArrayList <ServerClientThread> connectedClients;
    HashMap <String, String> idNamesMap; // {id ; clientname}
    int progressiveId;

    public ServerClientsHandler(){
        connectedClients = new ArrayList<>();
        idNamesMap = new HashMap<>();
        progressiveId = 0;
    }

    public void addClient(ServerClientThread client){
        idNamesMap.put(assignId(), client.clientName);
        connectedClients.add(client);

    }

    public void removeClient(){
            
    }

    private String assignId(){
        String id = String.format("%05d", progressiveId);
        progressiveId++;
        return id;
    }

    public String getIdByName(String name){
        String id = "";
        for(Entry<String, String> idname : idNamesMap.entrySet()){
            if(Objects.equals(name, idname.getValue()))
                return idname.getKey();
        }

        return id;
    }

    

    public HashMap<String,String> sendClientsMap(){

        return null;
    }


}