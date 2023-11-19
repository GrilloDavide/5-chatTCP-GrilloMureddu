package it.fi.meucci.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
            System.out.println("Inserire il nome utente");
            forwardMessageToServer(userInput.nextLine(), Prefix.CNT);
            do{
                communicate();
            }while(true);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void communicate() throws IOException{
        
        
        String userChoice;
        String message = "";
        Prefix prefix;
        userMenu();
        do{
            prefix = null;
            userChoice = userInput.nextLine();
            switch(userChoice){

                case "1": prefix = Prefix.PUB;
                    System.out.println("Scrivere il messaggio");
                    message = userInput.nextLine();

                    break;

                case "2": prefix = Prefix.PRV;
                    System.out.println("Scegliere l'utente con cui si vuole comunicare");
                    forwardMessageToServer("Lista richiesta da client", Prefix.LST);
                    message = MapHandler.getIdByName(userInput.nextLine(), clientParent.idNamesMap);
                    System.out.println("Scrivere il messaggio");
                    message += userInput.nextLine();
                    break;

                case "3": prefix = Prefix.LST;
                    message = "Richiesta da utente";
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

            if(prefix != null)
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

}
