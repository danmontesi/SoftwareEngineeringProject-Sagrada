package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshDraftPoolCommand extends ServerToClientCommand {

    public List<String> getDraftpool() {
        return draftpool;
    }

    private List<String> draftpool; //Dice in the format: colorNumber/empty

    public RefreshDraftPoolCommand(List<String> draftpool) {
        this.draftpool = draftpool;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
