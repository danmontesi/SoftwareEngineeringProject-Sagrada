package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class EndGameCommand extends ServerToClientCommand {

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
