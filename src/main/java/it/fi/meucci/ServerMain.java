package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(6789));

        server.serverStart();
    }
}
