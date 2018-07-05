package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class AuthenticatedCorrectlyCommand extends ServerToClientCommand{

    /**
     * Notifies the user that he authenticated successfully
     */
    public AuthenticatedCorrectlyCommand(String message) {
        this.message = message;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
