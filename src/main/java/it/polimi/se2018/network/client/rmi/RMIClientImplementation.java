package client.rmi;

import commands.ServerToClientCommand;

import java.util.Observable;

public class RMIClientImplementation extends Observable implements RMIClientInterface {


    @Override
    public void rmiNotifyClient(ServerToClientCommand command) {
        setChanged();
        notifyObservers(command);
        clearChanged();
    }

}
