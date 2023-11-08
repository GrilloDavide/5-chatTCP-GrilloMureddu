package it.fi.meucci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        Client client = new Client();

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


        switch(keyboard.readLine()){
            
        }

    }



    public void switchOptions(){
        System.out.println();
    }
}
