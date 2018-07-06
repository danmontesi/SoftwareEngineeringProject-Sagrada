package client.client_network.rmi;

import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.ServerToClientCommand;
import client.ClientController;
import shared.network_interfaces.RMIClientInterface;
import shared.network_interfaces.ServerConnection;
import shared.network_interfaces.RMIServerInterface;
import shared.utils.ControllerClientInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages RMI connections (client side)
 * @author Alessio Molinari
 */
public class RMIClient implements Remote, ServerConnection{

    private Registry registry;
    private RMIServerInterface server;
    private String ipAddress;

    public ControllerClientInterface getClientController() {
        return clientController;
    }

    private ClientController clientController;

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public RMIClient(int viewChoice, String ipAddress){
        clientController = new ClientController(viewChoice, this);
        this.ipAddress = ipAddress;
    }

    @Override
    public void send(ClientToServerCommand command) {
        new Thread(() -> {
            try {
                if (!command.hasUsername()) {
                    LOGGER.log(Level.FINE, "Connection not open yet: please start connection first");
                    return;
                }
                server.rmiSend(command);

            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }).start();
    }

    /**
     * Notify server that the connection is active
     */
    void notifyRMI(ServerToClientCommand command) {
        if (command.hasMessage() && command.getMessage().equals("Ping")){
            LOGGER.log(Level.FINE, "Arrived ping from server");
        }
        else
            clientController.dispatchCommand(command);
    }

    @Override
    public void startConnection(String username) {
        new Thread(() -> {
            try {
                registry = LocateRegistry.getRegistry(ipAddress, 1099);
                server = (RMIServerInterface) registry.lookup("RMIImplementation");
                RMIClientImplementation client = new RMIClientImplementation(this);
                RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
                server.addClient(remoteRef, username);
            } catch (RemoteException | NotBoundException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }).start();
    }
}