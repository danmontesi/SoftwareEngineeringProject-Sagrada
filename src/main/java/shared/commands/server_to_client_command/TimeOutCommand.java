package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class TimeOutCommand extends ServerToClientCommand{

    /**
     * Notifies the user that his turn time ended
     */
    public TimeOutCommand() {
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }
}
