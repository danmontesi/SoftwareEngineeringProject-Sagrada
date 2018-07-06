package shared.network_interfaces;


import shared.commands.server_to_client_command.ServerToClientCommand;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void rmiNotifyClient(ServerToClientCommand command) throws RemoteException;
}
