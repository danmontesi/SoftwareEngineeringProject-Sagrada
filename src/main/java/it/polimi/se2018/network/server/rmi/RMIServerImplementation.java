package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.Controller;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {

    //TODO devo hashmap - controller della partita


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

        //TODO Bisogna prendere il controller dalla hashmap del server
        //L'hashmap è fatta così: HashMap<ClientConnection, Controller> , per ogni connessione, il suo controller
        Controller.update(this, command);
    }

    @Override
    public void rmiSend(ClientToServerCommand command) throws RemoteException {
        send(command);
    }
}
