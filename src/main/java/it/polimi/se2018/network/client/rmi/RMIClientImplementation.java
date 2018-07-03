package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

public class RMIClientImplementation implements RMIClientInterface {

    private RMIClient client;

    RMIClientImplementation(RMIClient client){
        this.client = client;
    }

    @Override
    public void rmiNotifyClient(ServerToClientCommand command) {
        client.notifyRMI(command);
    }
}
