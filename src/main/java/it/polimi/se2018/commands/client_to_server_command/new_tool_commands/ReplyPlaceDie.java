package it.polimi.se2018.commands.client_to_server_command.new_tool_commands;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyPlaceDie extends ClientToServerCommand {

    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
