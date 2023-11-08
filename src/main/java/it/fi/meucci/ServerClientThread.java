package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ServerClientThread extends Thread{
  
    BufferedReader inClient;
    DataOutputStream outClient;
    String clientName;

    public ServerClientThread(Socket client) throws IOException{

        inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outClient = new DataOutputStream(client.getOutputStream());

    }
    

    @Override
    public void run() {
        try{
            
           communicate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void communicate() throws IOException{
        String nameString = inClient.readLine(); //The first line from a new client is the username
    }
}
