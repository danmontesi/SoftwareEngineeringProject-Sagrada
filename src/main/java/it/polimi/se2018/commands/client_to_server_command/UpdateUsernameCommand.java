package it.polimi.se2018.commands.client_to_server_command;


import it.polimi.se2018.utils.ControllerServerInterface;

public class UpdateUsernameCommand extends ClientToServerCommand{

    public UpdateUsernameCommand(String username){
        this.message = "UpdateUsernameCommand" + " " + username;
    }

    @Override
    public void visit(ControllerServerInterface observer) {
        //TODO MAYBE TO DELETE
    }
}
