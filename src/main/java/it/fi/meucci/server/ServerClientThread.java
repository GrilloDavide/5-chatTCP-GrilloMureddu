package it.fi.meucci.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;


public class ServerClientThread extends Thread{
  

    BufferedReader inClient;
    DataOutputStream outClient;
    Server serverParent;
    String clientName;

    Boolean connection = true;
    public ServerClientThread(Socket client, Server serverParent) throws IOException{
        this.serverParent = serverParent;
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
                System.out.println(ServerClientsHandler.idNamesMap);

            }while(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void messageToClient(String message) throws IOException {
        outClient.writeBytes(message+"\n");
    }

    private void interpretMessageType(String msg) throws IOException {
        Prefix messageType = Prefix.valueOf(msg.substring(0,3));
        String msgContent = msg.substring(3);

        switch(messageType){
            case CNT:
                clientName = msgContent;
                ServerClientsHandler.addClient(this);
                messageToClient(Prefix.CNT+clientName);
                break;
            case PUB:
                serverParent.forwardToAll(msgContent, MapHandler.getIdByName(clientName, ServerClientsHandler.idNamesMap));
                break;
            case PRV:
                serverParent.forwardMessage(msgContent, MapHandler.getIdByName(clientName, ServerClientsHandler.idNamesMap), Prefix.PRV);
                break;
            case LST:
                messageToClient(Prefix.LST+ServerClientsHandler.sendClientsMap());
                break;
            case DSC:
                messageToClient(Prefix.DSC+"");
                ServerClientsHandler.removeClient(this);
                closeThread();
                break;
            default:

        }
    }

    private void closeThread(){
        connection = false;
    }
}
