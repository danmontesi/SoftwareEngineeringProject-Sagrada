package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class EndGameCommand extends ServerToClientCommand {

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
