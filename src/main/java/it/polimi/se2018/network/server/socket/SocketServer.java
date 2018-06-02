package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final int port = 1111;

    ServerSocket serverSocket;

    public void socketStartListening(){
        try {
            serverSocket = new ServerSocket(1111);

            new Thread(){
                public void run(){
                    while(true){
                        Socket socket;
                        try {
                            socket = serverSocket.accept();
                            Server.addClientInterface(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
