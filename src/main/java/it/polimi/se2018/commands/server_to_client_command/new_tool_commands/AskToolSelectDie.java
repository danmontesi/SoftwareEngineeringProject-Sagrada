package it.polimi.se2018.commands.server_to_client_command.new_tool_commands;

import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.utils.ControllerClientInterface;

public class AskToolSelectDie extends ServerToClientCommand {

    public String getFrom() {
        return from;
    }

    private String from;
    public AskToolSelectDie(String from) {
        this.from = from;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
