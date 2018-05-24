package it.polimi.se2018.network;

import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public abstract class ServerConnection extends Observable implements Runnable {

    public String username;
    public void run(){

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void sendCommand(ServerToClientCommand command) throws IOException {

    }

    public void notifyController(ClientToServerCommand command){

    }

}
