package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class ChosenWindowPatternCard extends ClientToServerCommand{

    /**
     * Contains the message with NameClass + toolCardName
     */

    public ChosenWindowPatternCard(String message) {
        this.message = message;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}





