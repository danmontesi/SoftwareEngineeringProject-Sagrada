package it.polimi.se2018.network.client.socket;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.utils.ControllerClientInterface;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements ServerConnection {
    private static final int port = 11111;
    private static final String host = "127.0.0.1";
    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;
    ControllerClientInterface clientController;


    public SocketClient(int viewChoice){
        clientController = new ClientController(viewChoice, this);
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
    public void startConnection(String username){
        try {
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            //invia l'username al server per verificarne la validità
            output.writeObject(username);
            output.flush();

            new Thread(() -> {
                while (true){
                    try {
                        ServerToClientCommand command = (ServerToClientCommand) input.readObject();
                        System.out.println("SOCKET: arriva comando "+ command.toString());

                        new Thread(() -> {
                                clientController.dispatchCommand(command);
                            }).start();

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