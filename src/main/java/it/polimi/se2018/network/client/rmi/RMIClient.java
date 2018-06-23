package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.utils.ControllerClientInterface;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements Remote, ServerConnection{

    Registry registry;
    RMIServerInterface server;
    ControllerClientInterface clientController;

    public RMIClient(int viewChoice){
        clientController = new ClientController(viewChoice, this);
    }

    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                System.out.println("Connection not open yet: please start connection first");
                return;
            }
            server.rmiSend(command);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyRMI(ServerToClientCommand command){
        if (!command.toString().contains("Ping")) {
            System.out.println("RMI: arriva comando " + command.toString());
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
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}