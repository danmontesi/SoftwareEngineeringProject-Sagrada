package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.utils.ControllerClientInterface;

public class OtherPlayerTurnCommand extends ServerToClientCommand {

    private String username;

    public OtherPlayerTurnCommand(String username) {
        this.username = username;
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
