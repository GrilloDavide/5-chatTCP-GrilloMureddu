package it.fi.meucci.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedHashMap;

import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;

public class Client {

    String username;
    Socket socket;
    BufferedReader inServer;
    ClientOutputThread outServer;
    LinkedHashMap<String, String> idNamesMap;
    Boolean connection = true;

    public Client(Socket socket) throws IOException{
        this.socket = socket;
        
    }
    
    public void connect() throws IOException{
        
        inServer = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        outServer = new ClientOutputThread(socket.getOutputStream(), this);


    }

    public void communicate() throws IOException{
        outServer.start();
        
        do{

            interpretMessage(inServer.readLine());


        }while(connection);
    }


    private void interpretMessage(String msg) throws IOException {

        Prefix messageType = Prefix.valueOf(msg.substring(0,3));
        String msgContent = msg.substring(3);

        switch(messageType){

            case PUB, PRV:
                String id=msgContent.substring(0,5);
                msgContent = msgContent.substring(5);

                System.out.println("["+MapHandler.getNameById(id, idNamesMap)+"] "+messageType+": "+msgContent);
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
        idNamesMap = MapHandler.stringToHashMap(stringedMap);
    }

    private void closeClient() {
        System.out.println("Comunicazione terminata");
        connection = false;
    }
}
