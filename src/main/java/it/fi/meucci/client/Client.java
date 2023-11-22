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
    LinkedHashMap<String, String> idNamesMap = new LinkedHashMap<>();
    Boolean connection = true;

    public Client(Socket socket) throws IOException{
        this.socket = socket;
        
    }
    
    public void connect() throws IOException{
        
        inServer = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        outServer = new ClientOutputThread(socket.getOutputStream(), this);


    }

    public void communicate() throws IOException{
        

        do {
            // login
            
            outServer.login();
            
        
            // quando il server mi dice ok
        } while (!loginOk());



        outServer.start();
        
        do{

            interpretMessage(inServer.readLine());


        }while(connection);

        outServer.interrupt();
        inServer.close();
    }


    private boolean loginOk() throws IOException  {
        String msg = inServer.readLine();
        String msgContent = msg.substring(3);
        boolean check = !(msgContent.equals("ERROR"));
        if(!check)
            System.out.println("Errore: nome utente gia' in uso; inserirne un altro");
        return check;
    }

    private void interpretMessage(String msg) throws IOException {

        Prefix messagePrefix = Prefix.valueOf(msg.substring(0,3));
        String msgContent = msg.substring(3);

        switch(messagePrefix){
            
            case CNT:
                outServer.setName(msgContent);
            break;
            case PUB, PRV:
                String messageType = "a te ";
                String id=msgContent.substring(0,5);
                msgContent = msgContent.substring(5);
                if(messagePrefix == Prefix.PUB)
                    messageType = "a tutti";
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
