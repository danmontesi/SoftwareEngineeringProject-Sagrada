package server.server_network.socket;


import server.Server;
import shared.CONSTANTS;

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
    private static final int port = CONSTANTS.SOCKET_PORT;

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
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                            output.flush();
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                            String username = (String) input.readObject();
                            Server.addClientInterface(socket, input, output, username);
                        } catch (IOException e) {
                            //nothing
                        } catch (ClassNotFoundException e) {
                            //nothing
                        }

                    }
                }
            }.start();
        } catch (IOException e) {
            //nothing
        }
    }

}