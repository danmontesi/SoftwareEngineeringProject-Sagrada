package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.MVC.Controller;

public class UpdateUsernameCommand extends ClientToServerCommand{
    private String username;

    public String getUsername() {
        return username;
    }

    public UpdateUsernameCommand(String u){
        this.username = u;
    }

    public void execute(Controller controller){
        controller.applyClientCommand(this);
    }


}
