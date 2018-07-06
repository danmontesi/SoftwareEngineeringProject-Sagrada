package client.client_network.rmi;

import shared.commands.server_to_client_command.ServerToClientCommand;
import shared.network_interfaces.RMIClientInterface;

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
