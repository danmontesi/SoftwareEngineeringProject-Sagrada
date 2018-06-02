package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.network.ServerConnection;

public class UpdateUsernameCommand extends ClientToServerCommand{
    private String message;

    public UpdateUsernameCommand(String username){
        this.message = "UpdateUsernameCommand" + username;
    }



}
