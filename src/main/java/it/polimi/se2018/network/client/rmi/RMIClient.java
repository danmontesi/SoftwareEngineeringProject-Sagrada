package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements ServerConnection {

    Registry registry;
    RMIServerInterface server;

    public RMIClient(){
    }

    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                System.out.println("Connection not open yet: please start connection first");
                return;
            }
            System.out.println("Sending " + command.getMessage());
            server.rmiSend(command);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startConnection(String username) {
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (RMIServerInterface)registry.lookup("RMIImplementation");
            RMIClientImplementation client = new RMIClientImplementation(username, this);//Viene passato anche this così può rispondere nel caso in cui riceve un AskUsernameCommand
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            server.addClient(remoteRef, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}