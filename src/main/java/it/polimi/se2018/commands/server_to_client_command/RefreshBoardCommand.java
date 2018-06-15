package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.utils.ControllerClientInterface;

public class RefreshBoardCommand extends ServerToClientCommand{
    public Model getModel() {
        return model;
    }

    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */
    private Model model;

    public RefreshBoardCommand(String model){
        this.message = model;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
