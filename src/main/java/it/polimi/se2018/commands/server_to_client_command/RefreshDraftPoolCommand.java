package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshDraftPoolCommand extends ServerToClientCommand {
    private List<String> draftPool;

    /**
     * Contains a String representation of the Draft Pool dice
     */
    public RefreshDraftPoolCommand(List<String> draftPool) {
        this.draftPool = draftPool;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public List<String> getDraftPool() {
        return draftPool;
    }
}
