package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class TimeOutCommand extends ServerToClientCommand{

    public TimeOutCommand() {
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
