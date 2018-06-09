package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class MoveChoicePassTurn extends ClientToServerCommand{


    /**
     * Move performed in case there is no way to place a correct die in the wpc
     */

    public MoveChoicePassTurn(String message) {
        this.message = message;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
