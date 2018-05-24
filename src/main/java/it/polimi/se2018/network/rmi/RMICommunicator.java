package it.polimi.se2018.network.rmi;

import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Specifies all methods that RMI interface offers to client, of course, without implementations
 * Implementations are written in class Communicator
 */
public interface RMICommunicator extends Remote {
    void executeCommand(ClientToServerCommand clientToServerCommand) throws RemoteException;
}
