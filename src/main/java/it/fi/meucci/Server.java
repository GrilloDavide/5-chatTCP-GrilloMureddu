package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    
    public ServerClientsHandler clientsHandler;

    ServerSocket serverSckt;



    public Server(ServerSocket serverSckt){
        this.serverSckt = serverSckt;
        clientsHandler = new ServerClientsHandler();
    }

    public void serverStart() throws IOException{
        for(;;){
            System.out.println( " Server in attesa di un client " );
            Socket client = serverSckt.accept();
            System.out.println("Il client "+client.getPort()+" si è collegato");
            ServerClientThread thread = new ServerClientThread(client);
            thread.start();
        }
    }


    public static class ServerClientsHandler {
    
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
    
    }
}
