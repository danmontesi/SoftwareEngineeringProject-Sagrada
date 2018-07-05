package server.server_network.rmi;


import shared.commands.client_to_server_command.ClientToServerCommand;
import client.client_network.rmi.RMIClientInterface;
import server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {

    RMIServerImplementation() throws RemoteException {
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