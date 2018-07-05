package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class MoveChoicePassTurn extends ClientToServerCommand{
    /**
     * Move performed when the user wants to pass
     */
    public MoveChoicePassTurn() {}

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
