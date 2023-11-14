package it.fi.meucci;

import java.io.BufferedReader;
import java.util.HashMap;

public class ClientOutputThread extends Thread{
    Client client;
    String outputId;
    BufferedReader outputStream;
    HashMap<String,String> idNamesMap;

    public ClientOutputThread(String outputId){
        this.outputId = outputId;
    }
}
