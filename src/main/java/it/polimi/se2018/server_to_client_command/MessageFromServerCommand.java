package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class MessageFromServerCommand extends ServerToClientCommand {

    /**
     * Class for generic message from the controller
     * @param message message to show
     */
    public MessageFromServerCommand(String message) {
        this.message=message;
    }
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
