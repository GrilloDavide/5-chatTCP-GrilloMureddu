package it.fi.meucci.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import it.fi.meucci.Prefix;


public class ServerClientThread extends Thread{
  
    ServerClientsHandler clientsHandler;
    BufferedReader inClient;
    DataOutputStream outClient;
    String clientName;
    String clientId;
    public ServerClientThread(Socket client, ServerClientsHandler clientsHandler) throws IOException{
        this.clientsHandler = clientsHandler;
        inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outClient = new DataOutputStream(client.getOutputStream());

    }
    

    @Override
    public void run() {
        try{
            
           do{
                String msgIn = inClient.readLine();
                interpretMessageType(msgIn);
                System.out.println(msgIn);

            }while(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void forwardToPrivate(String message){
        
    }

    public void forwardToAll(String message){

    }

    private void interpretMessageType(String msg){
        Prefix messageType = Prefix.valueOf(msg.substring(0,3));
        String msgContent = msg.substring(3);

        switch(messageType){
            case CNT:
                clientName = msgContent;
                clientsHandler.addClient(this);
                break;
            case PUB:
                forwardToAll(msgContent);
                break;
            case PRV:
                forwardToPrivate(msgContent);
                break;
            case LST:
                clientsHandler.sendClientsMap();
                break;
            case DSC:
                clientsHandler.removeClient();
                
                break;
            default:

        }
    }
}
