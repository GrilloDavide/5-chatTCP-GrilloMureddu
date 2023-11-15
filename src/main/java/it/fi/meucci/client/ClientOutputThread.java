package it.fi.meucci.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import it.fi.meucci.Prefix;


public class ClientOutputThread extends Thread{
    
    Scanner userInput;
    DataOutputStream outServer;


    public ClientOutputThread(OutputStream outputStream){
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
        Prefix prefix = null;
        do{
            userMenu();
            userChoice = userInput.nextLine();
            switch(userChoice){
               
                case "1": prefix = Prefix.PUB;
                    System.out.println("Scrivere il messaggio");
                    message = userInput.nextLine();

                    break;
                case "2": prefix = Prefix.PRV;
                    System.out.println("Scegliere l'utente con cui si vuole comunicare");

                    System.out.println("Scrivere il messaggio"); 
                    message = userInput.nextLine();
                    break;
                case "3": prefix = Prefix.LST;
                    
                    break;
                case "0": prefix = Prefix.DSC;
                    
                    break; 
                default:
                    prefix = null;
            }
            if(prefix != null)
                forwardMessageToServer(message, prefix);
        }while(userChoice != "0");
        
    }

    private void forwardMessageToServer(String message, Prefix prefix) throws IOException{
        
        outServer.writeBytes(prefix+message+"\n");

    }

    private void userMenu(){
        System.out.println("INSERIRE IL NUMERO CORRISPONDENTE ALL'AZIONE RICHIESTA");
        System.out.println("(1) - Per scrivere un messaggio a tutti gli utenti connessi");
        System.out.println("(2) - Per scrivere un messaggio ad un utente specifico");
        System.out.println("(3) - Per richiedere la lista degli utenti connessi");
        System.out.println("(0) - Per abbandonare la chat");
    }



}
