package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

public class RMIClientImplementation implements RMIClientInterface {


    public RMIClientImplementation(){
    }

    @Override
    public void rmiNotifyClient(ServerToClientCommand command) {
        ClientController.update(command);
    }

}
