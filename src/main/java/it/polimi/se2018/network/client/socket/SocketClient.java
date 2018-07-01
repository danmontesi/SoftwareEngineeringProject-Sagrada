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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements ServerConnection {
    private static final int port = 11111;
    private static String host = "127.0.0.1";
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ControllerClientInterface clientController;
    private boolean isAlive = true;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());



    public SocketClient(int viewChoice, String ipAddress){
        clientController = new ClientController(viewChoice, this);
        host = ipAddress;
    }

    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                LOGGER.log(Level.INFO, "Connection not open yet: please start connection first");
                return;
            }
            output.writeObject(command);
            output.flush();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
                while (isAlive){
                    try {
                        ServerToClientCommand command = (ServerToClientCommand) input.readObject();
                        if (command.hasMessage() && command.getMessage().contains("Ping")) {
                            LOGGER.log(Level.FINE," SOCKET: arriva comando ", command.getMessage());
                        }
                        else {
                            clientController.dispatchCommand(command);
                        }
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                        isAlive = false;
                    } catch (ClassNotFoundException e) {
                        //nothing
                    }
                }
            }).start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }
}