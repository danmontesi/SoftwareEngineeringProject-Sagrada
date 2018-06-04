package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.rmi.RemoteException;

public class RMIVirtualClient implements ClientConnection {

    RMIClientInterface rmiClientInterface;

    public RMIVirtualClient(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void notifyClient(ServerToClientCommand command) {
        try{
            System.out.println("entro nel RMIVirtualClient");
            rmiClientInterface.rmiNotifyClient(command);
        } catch (RemoteException e){
            Server.removeClient(this);
            System.out.println("Client disconnesso");
        }
    }
}

// bella