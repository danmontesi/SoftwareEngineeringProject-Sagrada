package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{
    void rmiSend(ClientToServerCommand command) throws RemoteException;
    void addClient(RMIClientInterface client, String username) throws RemoteException;
}