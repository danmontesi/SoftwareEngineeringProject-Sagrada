package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class InvalidActionCommand extends ServerToClientCommand{

    /**
     * Notifies the user he performed an invalid action
     */
    public InvalidActionCommand(String message) {
        this.message= message;
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
