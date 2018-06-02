package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.util.Observable;

public class RMIClientImplementation extends Observable implements RMIClientInterface {


    @Override
    public void rmiNotifyClient(ServerToClientCommand command) {
        setChanged();
        notifyObservers(command);
        clearChanged();
    }

}
