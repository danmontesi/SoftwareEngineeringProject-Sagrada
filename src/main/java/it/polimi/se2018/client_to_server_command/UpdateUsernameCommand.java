package it.polimi.se2018.client_to_server_command;

public class UpdateUsernameCommand extends ClientToServerCommand{
    private String username;

    public String getUsername() {
        return username;
    }

    public UpdateUsernameCommand(String u){
        this.username = u;
    }


}
