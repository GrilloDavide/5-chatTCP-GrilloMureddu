package it.fi.meucci;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerClientsHandler {
    
    ArrayList <ServerClientThread> connectedClients;
    HashMap <String, String> idNamesMap; // {id ; clientname}
    int progressiveId;

    public ServerClientsHandler(){
        connectedClients = new ArrayList<>();
        progressiveId = 0;
    }

    public void addClient(ServerClientThread client){
        idNamesMap.put(assignId(), client.clientName);
        connectedClients.add(client);

    }

    private String assignId(){
        String id = String.format("%05d", progressiveId);
        progressiveId++;
        return id;
    }

    public HashMap<String,String> sendClientsMap(){

        return null;
    }


}