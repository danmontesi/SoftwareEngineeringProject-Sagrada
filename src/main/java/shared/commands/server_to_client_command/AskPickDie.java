package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class AskPickDie extends ServerToClientCommand {
    private String from;

    /**
     * Contains where the user can pick the die from (Window Pattern Card, Draft Pool or Round Track)
     */
    public AskPickDie(String from) {
        this.from = from;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public String getFrom() {
        return from;
    }
}
