package it.polimi.se2018.commands.client_to_server_command.new_tool_commands;

import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyPickDie extends ClientToServerCommand {

    public ReplyPickDie(Integer index) {
        this.index = index;
    }

    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}
