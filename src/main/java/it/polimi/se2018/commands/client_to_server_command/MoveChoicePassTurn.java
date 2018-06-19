package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class MoveChoicePassTurn extends ClientToServerCommand{


    /**
     * Move performed in case there is no way to place a correct die in the wpc
     */

    public MoveChoicePassTurn(String username) {
        this.username = username;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
