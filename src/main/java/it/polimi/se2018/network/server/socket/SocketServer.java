package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Manages Socket connections (server side)
 * @author Alessio Molianri
 */
public class SocketServer {
    private static final int port = 11111;

    ServerSocket serverSocket;

    /**
     * Starts listening
     */
    public void socketStartListening(){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening socket, address: " + InetAddress.getLocalHost() + " port: " + serverSocket.getLocalPort());

            new Thread(){
                public void run(){
                    while(true){
                        Socket socket;
                        try {
                            socket = serverSocket.accept();
                            System.out.println("Accettato Socket");
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                            output.flush();
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                            String username = (String) input.readObject();
                            System.out.println("Letto username: "+ username);
                            Server.addClientInterface(socket, input, output, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
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