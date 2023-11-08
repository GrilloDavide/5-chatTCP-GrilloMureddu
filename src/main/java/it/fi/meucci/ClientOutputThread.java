package it.fi.meucci;

import java.io.BufferedReader;

public class ClientOutputThread extends Thread{
    Client client;
    String outputId;
    BufferedReader outputStream;

    public ClientOutputThread(String outputId){
        this.outputId = outputId;
    }
}
