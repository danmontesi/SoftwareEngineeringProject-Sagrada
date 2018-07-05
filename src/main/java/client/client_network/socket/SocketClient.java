package client.client_network.socket;


import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.PingConnectionTester;
import shared.commands.server_to_client_command.ServerToClientCommand;
import client.ClientController;
import server.server_network.ServerConnection;
import shared.utils.ControllerClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages Socket connections (client side)
 * @author Alessio Molinari
 */
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
                        if (!(command instanceof PingConnectionTester)) {
                            ServerToClientCommand toDispatch = command;
                            clientController.dispatchCommand(toDispatch);
                        }
                        else {
                            LOGGER.log(Level.FINE,"Arrived ping from server");
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