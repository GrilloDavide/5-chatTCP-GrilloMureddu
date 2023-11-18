package it.fi.meucci.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import it.fi.meucci.HashMapConverter;
import it.fi.meucci.Prefix;

public class Client {

    String username;
    Socket socket;
    BufferedReader inServer;
    ClientOutputThread outServer;
    HashMap <String, String> idNamesMap;

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
            
            case PUB, PRV:
                String name=msgContent.substring(0,5);
                msgContent = msgContent.substring(5);

                System.out.println("["+convertIdToName(name)+"] "+messageType+": "+msgContent);
                break;
            case LST:
                updateIdMap(msgContent);
                break;
            case DSC:
                closeClient();
                break;
            default:

        }
    }

    private void updateIdMap(String stringedMap){
        idNamesMap = HashMapConverter.stringToHashMap(stringedMap);
    }

    private String convertIdToName(String name){

        for (HashMap.Entry<String, String> entry : idNamesMap.entrySet()){
            if(entry.getValue().equals(name))
                return entry.getKey();
        }

        return null;
    }



    private void closeClient() {

    }
}
