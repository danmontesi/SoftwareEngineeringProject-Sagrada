package it.polimi.se2018.commands.client_to_server_command;


public class UpdateUsernameCommand extends ClientToServerCommand{

    public UpdateUsernameCommand(String username){
        this.message = "UpdateUsernameCommand" + " " + username;
    }

}