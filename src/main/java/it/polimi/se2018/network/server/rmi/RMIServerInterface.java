package server.rmi;

import client.rmi.RMIClientInterface;
import commands.ClientToServerCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{
    void rmiSend(ClientToServerCommand command) throws RemoteException;
    void addClient(RMIClientInterface client) throws RemoteException;
}
