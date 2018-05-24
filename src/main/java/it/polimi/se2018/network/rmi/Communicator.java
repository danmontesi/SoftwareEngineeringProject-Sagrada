package it.polimi.se2018.network.rmi;

import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

import java.rmi.RemoteException;

/**
 * Specifies every implementation in RMICommunicator
 * THIS IS THE STUB
 */
public class Communicator implements RMICommunicator {
    public Communicator() {
    }

    @Override
    public void executeCommand(ClientToServerCommand clientToServerCommand) throws RemoteException {
        System.out.println("RMI: eseguo il comando ricevuto");
    }

    //TODO: ci saranno altri metodi da aggiungere e da implementare
    //ricorda di scrivere i metodi nell'interfaccia RMICommunicator
    //e implementarli in questa classe con override
}
