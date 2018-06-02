package server.rmi;

import client.ClientConnection;
import client.rmi.RMIClientInterface;
import commands.ServerToClientCommand;
import server.Server;

import java.rmi.RemoteException;

public class RMIVirtualClient implements ClientConnection {

    RMIClientInterface rmiClientInterface;

    public RMIVirtualClient(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void notifyClient(ServerToClientCommand command) {
        try{
            rmiClientInterface.rmiNotifyClient(command);
        } catch (RemoteException e){
            Server.removeClient(this);
            System.out.println("Client disconnesso");
        }
    }
}
