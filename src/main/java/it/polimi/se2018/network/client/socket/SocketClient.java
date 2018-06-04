package it.polimi.se2018.network.client.socket;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements ServerConnection {
    private static final int port = 1111;
    private static final String host = "127.0.0.1";
    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    public SocketClient(){
    }
    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                System.out.println("Connection not open yet: please start connection first");
                return;
            }
            output.writeObject(command);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startConnection(String username) {
        try {
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            //invia l'username al server per verificarne la validità
            output.writeObject(username);
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            new Thread(() -> {
                while (true){
                    try {
                        ServerToClientCommand command = (ServerToClientCommand) input.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        continue;
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}