package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyPlaceDie extends ClientToServerCommand {

    public ReplyPlaceDie(Integer position) {
        this.position = position;
    }

    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
