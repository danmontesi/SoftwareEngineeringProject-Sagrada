package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.Controller;
import it.polimi.se2018.network.server.Server;

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
