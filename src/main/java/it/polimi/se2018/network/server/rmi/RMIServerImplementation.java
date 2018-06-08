package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {

    public RMIServerImplementation() throws RemoteException {
        //port:0 means port is chosen automatically
        super(0);
    }

    @Override
    public void addClient(RMIClientInterface client, String username) throws RemoteException {
        Server.addClientInterface(client, username);
    }

    @Override
    public void rmiSend(ClientToServerCommand command) throws RemoteException {
        Server.handle(command);
    }
}