package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void rmiNotifyClient(ServerToClientCommand command) throws RemoteException;
}
