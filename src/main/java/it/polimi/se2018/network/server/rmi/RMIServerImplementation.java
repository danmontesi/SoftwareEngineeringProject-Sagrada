package server.rmi;

import client.rmi.RMIClientInterface;
import commands.ClientToServerCommand;
import server.Controller;
import server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {

    private Controller controller;

    public RMIServerImplementation() throws RemoteException {
        //port:0 means port is chosen automatically
        super(0);
    }



    @Override
    public void addClient(RMIClientInterface client) throws RemoteException {
        Server.addClientInterface(client);
        System.out.println("Client "+ (Server.getClients().indexOf(client)+1) + " connesso");
    }


    public void send(ClientToServerCommand command) {
        //TODO: Da verificarne la fattibilità
        Controller.stampa(command);
    }
    @Override
    public void rmiSend(ClientToServerCommand command) throws RemoteException {
        send(command);
    }
}
