package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyDieValue extends ClientToServerCommand {

    public ReplyDieValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    private Integer value;

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
