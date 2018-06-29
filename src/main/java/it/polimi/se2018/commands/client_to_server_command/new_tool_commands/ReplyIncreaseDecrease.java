package it.polimi.se2018.commands.client_to_server_command.new_tool_commands;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyIncreaseDecrease extends ClientToServerCommand {

    public boolean isIncrease() {
        return increase;
    }

    private boolean increase;

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
