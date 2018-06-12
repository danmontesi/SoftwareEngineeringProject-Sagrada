package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.utils.ControllerClientInterface;

public class InvalidUseToolCircularCutter extends ServerToClientCommand{


    public InvalidUseToolCircularCutter(String message) {
        this.message=message;
    }

    /**
     * Visitor method for dynamic binding
     * @param clientController
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }


}
