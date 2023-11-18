package it.fi.meucci.server;

import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
            ServerClientThread thread = new ServerClientThread(client, this);
            thread.start();
        }
    }

    public void forwardMessage(String idMessage, String senderId, Prefix prefix) throws IOException {
        String id = idMessage.substring(0,5);

        String message = prefix+senderId+idMessage.substring(5);
        int index = MapHandler.getPositionById(id, ServerClientsHandler.idNamesMap);
        ServerClientsHandler.connectedClients.get(index).messageToClient(message);

    }

    public void forwardToAll(String idMessage){
       ServerClientsHandler.connectedClients.forEach(a -> {
           try {
               forwardMessage(idMessage, MapHandler.getIdByName(a.clientName, ServerClientsHandler.idNamesMap), Prefix.PUB);
           } catch (IOException e) {
               System.out.println("DIOBOIA");
               throw new RuntimeException(e);
           }
       });
    }
    
}
