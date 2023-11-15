package it.fi.meucci.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import it.fi.meucci.Prefix;

public class Client {

    String username;
    Socket socket;
    BufferedReader inServer;
    ClientOutputThread outServer;


    public Client(Socket socket) throws IOException{
        this.socket = socket;
        
    }
    
    public void connect() throws IOException{
        
        inServer = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        outServer = new ClientOutputThread(socket.getOutputStream());
        
    }

    public void communicate() throws IOException{
        outServer.start();
        
        do{

            interpretMessage(inServer.readLine());

        }while(true);
    }


    private void interpretMessage(String msg){
        Prefix messageType = Prefix.valueOf(msg.substring(0,3));
        String msgContent = msg.substring(3);

        switch(messageType){
            
            case PUB:
                System.out.println(msgContent);
                break;
            case PRV:
                
                break;
            case LST:
                
                break;
            case DSC:
                
                
                break;
            default:

        }
    }
}
