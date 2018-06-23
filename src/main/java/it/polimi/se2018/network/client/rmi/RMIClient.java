package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.utils.ControllerClientInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient implements Remote, ServerConnection{

    private Registry registry;
    private RMIServerInterface server;
    private ControllerClientInterface clientController;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());


    public RMIClient(int viewChoice){
        clientController = new ClientController(viewChoice, this);
    }

    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                LOGGER.log(Level.INFO, "Connection not open yet: please start connection first");
                return;
            }
            server.rmiSend(command);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void notifyRMI(ServerToClientCommand command){
        if (!command.toString().contains("Ping")) {
            LOGGER.log(Level.FINE, "RMI: arriva comando", command);
        }
        clientController.dispatchCommand(command);
    }

    @Override
    public void startConnection(String username) {
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (RMIServerInterface)registry.lookup("RMIImplementation");
            RMIClientImplementation client = new RMIClientImplementation(this);
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            server.addClient(remoteRef, username);
        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}