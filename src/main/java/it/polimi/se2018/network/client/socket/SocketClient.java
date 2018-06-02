package it.polimi.se2018.network.client.socket;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.server.ServerConnection;

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
    //username identificativo
    String username;

    @Override
    public void send(ClientToServerCommand command) {
        try {
            command.setUsername(username);
            output.writeObject(command);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startConnection() {
        try {
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            new Thread(() -> {
                while (true){
                    try {
                        input.readObject();
                        //TODO: bisogna passare questo comando al controller
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
