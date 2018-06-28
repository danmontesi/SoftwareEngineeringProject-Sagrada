package it.polimi.se2018.commands.client_to_server_command.new_tool_commands;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolDecideValue extends ClientToServerCommand {

    public Integer getValue() {
        return value;
    }

    private Integer value;

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
