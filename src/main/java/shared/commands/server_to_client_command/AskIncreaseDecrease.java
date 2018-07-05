package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class AskIncreaseDecrease extends ServerToClientCommand {

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
