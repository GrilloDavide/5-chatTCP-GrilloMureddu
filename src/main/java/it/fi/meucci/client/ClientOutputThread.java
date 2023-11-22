package it.fi.meucci.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;

import it.fi.meucci.MapHandler;
import it.fi.meucci.Prefix;


public class ClientOutputThread extends Thread{
    
    Scanner userInput;
    DataOutputStream outServer;
    Client clientParent;

    public ClientOutputThread(OutputStream outputStream, Client clientParent){
        this.clientParent = clientParent;
        userInput = new Scanner(System.in);
        outServer = new DataOutputStream(outputStream);
    }

    @Override
    public void run(){
        try {
            //connect();

            communicate();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }


    public void login() throws IOException{
        String username = "";
        
        System.out.println("Inserire il nome utente");
        
        username = userInput.nextLine().toLowerCase(); // Tutti gli username sono salvati in lowercase
        
        forwardMessageToServer(username, Prefix.CNT);
    }

    private void communicate() throws IOException{
        
        
        String userChoice;
        String message = "";
        String userToCommunicate = "";
        Prefix prefix;
        
        forwardMessageToServer("ListaClient", Prefix.LST);
        userMenu();
        do{
            prefix = null;
            userChoice = userInput.nextLine();
            switch(userChoice){

                case "1": 
                    if(checkIfAlone()){
                        System.out.println("Errore: non ci sono altri utenti a cui scrivere");
                        break;
                    }

                    prefix = Prefix.PUB;

                    System.out.println("Scrivere il messaggio");
                    message = userInput.nextLine();

                    break;

                case "2": 
                    if(checkIfAlone()){
                        System.out.println("Errore: non ci sono altri utenti a cui scrivere");
                        break;
                    }

                    prefix = Prefix.PRV;

                    System.out.println("Scegliere l'utente con cui si vuole comunicare");
                    printConnectedUsers();

                    do { //Controllo del destinatario; non può essere uguale al mittente
                        userToCommunicate = userInput.nextLine().toLowerCase();
                        if(userToCommunicate.equals(super.getName()))
                            System.out.println("Errore: non e' possibile scrivere a se stessi");
                    } while (userToCommunicate.equals(super.getName()));

                    message = MapHandler.getIdByName(userToCommunicate, clientParent.idNamesMap);
                    System.out.println("Scrivere il messaggio");
                    message += userInput.nextLine();
                    break;

                case "3": prefix = Prefix.LST;
                    forwardMessageToServer("ListaUtente", Prefix.LST);
                    printConnectedUsers();
                    break;

                case "0": prefix = Prefix.DSC;
                    message = "TERMINA";
                    break;

                case "HELP","H","help" :
                    userMenu();
                    break;
                default:
                    prefix = null;
                    System.out.println("Errore: nessun numero e' associato a questa azione");
            }

            if(!(prefix == null || prefix == Prefix.LST))
                forwardMessageToServer(message, prefix);

        }while(!(userChoice.equals("0")));
        
    }

    public void forwardMessageToServer(String message, Prefix prefix) throws IOException{
        outServer.writeBytes(prefix+message+"\n");
    }

    private void userMenu(){
        System.out.println("INSERIRE IL NUMERO CORRISPONDENTE ALL'AZIONE RICHIESTA");
        System.out.println("(1) - Per scrivere un messaggio a tutti gli utenti connessi");
        System.out.println("(2) - Per scrivere un messaggio ad un utente specifico");
        System.out.println("(3) - Per richiedere la lista degli utenti connessi");
        System.out.println("(0) - Per abbandonare la chat");
        System.out.println("Scrivere HELP per visualizzare nuovamente questa lista");
    }

    private void printConnectedUsers(){
        System.out.println("LISTA DEGLI USER CONNESSI");
        for (Map.Entry<String, String> entry : clientParent.idNamesMap.entrySet()){
            System.out.print("- "+entry.getValue());
            if(super.getName().equals(entry.getValue()))
                System.out.print(" (TU)");
            System.out.println("");
        }
    }

    private boolean checkIfAlone(){
        return clientParent.idNamesMap.size() <= 1;
    }

}
