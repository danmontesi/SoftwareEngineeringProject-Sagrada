package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.client_to_server_command.UpdateUsernameCommand;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.util.Observable;

public class RMIClientImplementation extends Observable implements RMIClientInterface {

    String username;
    RMIClient client;

    public RMIClientImplementation(String username, RMIClient client){
        this.username = username;
        this.client = client;
    }

    @Override
    public void rmiNotifyClient(ServerToClientCommand command) {
        if (command.getMessage().split(" ")[0].equals("AskAuthenticationCommand")){
            client.send(new UpdateUsernameCommand(command.getMessage().split(" ")[1] + " "+ this.username)); //Setting old username + new username, so the server knows
        }
        else {
            setChanged();
            notifyObservers(command);
            clearChanged();
        }
    }

}
