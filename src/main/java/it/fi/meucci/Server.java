package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            ServerClientThread thread = new ServerClientThread(client, clientsHandler);
            thread.start();
        }
    }


    
}
