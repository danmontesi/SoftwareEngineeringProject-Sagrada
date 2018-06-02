package client.rmi;

import commands.ServerToClientCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void rmiNotifyClient(ServerToClientCommand command) throws RemoteException;
}
