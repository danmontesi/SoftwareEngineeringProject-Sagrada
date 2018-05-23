package it.polimi.se2018.network;

import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.IOException;

public abstract class ServerConnection implements Runnable{

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
}
