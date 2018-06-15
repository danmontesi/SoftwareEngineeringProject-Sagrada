package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class InvalidActionCommand extends ServerToClientCommand{
    public InvalidActionCommand(String message) {
        this.message= message;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
