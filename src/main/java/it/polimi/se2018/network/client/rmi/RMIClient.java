package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements ServerConnection {

    Registry registry;
    RMIServerInterface server;
    //username identificativo
    String username;

    @Override
    public void send(ClientToServerCommand command) {
        try {
            //TODO: Gestire l'username nullo
            command.setUsername(username);
            server.rmiSend(command);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startConnection() {
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (RMIServerInterface)registry.lookup("RMIImplementation");
            RMIClientImplementation client = new RMIClientImplementation();
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            server.addClient(remoteRef);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
