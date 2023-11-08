package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ServerClientThread extends Thread{
  
    ServerClientsHandler clientsHandler;
    BufferedReader inClient;
    DataOutputStream outClient;
    String clientName;

    public ServerClientThread(Socket client, ServerClientsHandler clientsHandler) throws IOException{
        this.clientsHandler = clientsHandler;
        inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outClient = new DataOutputStream(client.getOutputStream());

    }
    

    @Override
    public void run() {
        try{
            clientName = inClient.readLine();
            clientsHandler.addClient(this);
            String msg;
            for(;;){
                msg = inClient.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void forwardMessage(String msg) throws IOException{
        String nameString = inClient.readLine(); //The first line from a new client is the username
    }


    private void recognizeMsgType(String msg){

    }
}
