package server.server_network.rmi;


import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.network_interfaces.RMIClientInterface;
import server.Server;
import shared.network_interfaces.RMIServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {

    RMIServerImplementation() throws RemoteException {
        //port:0 means port is chosen automatically
        super(0);
    }

    @Override
    public void addClient(RMIClientInterface client, String username){
        Server.addClientInterface(client, username);
    }

    @Override
    public void rmiSend(ClientToServerCommand command){
        Server.handle(command);
    }
}