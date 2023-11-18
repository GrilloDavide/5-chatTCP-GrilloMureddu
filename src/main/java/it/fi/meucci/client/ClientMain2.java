package it.fi.meucci.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMain2 {
    public static void main(String[] args) throws IOException {

        Client client = new Client(new Socket(InetAddress.getLocalHost() , 6789));
        client.connect();
        client.communicate();
        
    }
}
