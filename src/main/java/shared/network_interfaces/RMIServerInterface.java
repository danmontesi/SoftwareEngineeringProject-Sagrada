package shared.network_interfaces;


import shared.commands.client_to_server_command.ClientToServerCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{
    void rmiSend(ClientToServerCommand command) throws RemoteException;
    void addClient(RMIClientInterface client, String username) throws RemoteException;
}