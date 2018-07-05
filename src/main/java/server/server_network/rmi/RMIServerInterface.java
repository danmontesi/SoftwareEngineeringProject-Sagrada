package server.server_network.rmi;


import shared.commands.client_to_server_command.ClientToServerCommand;
import client.client_network.rmi.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{
    void rmiSend(ClientToServerCommand command) throws RemoteException;
    void addClient(RMIClientInterface client, String username) throws RemoteException;
}