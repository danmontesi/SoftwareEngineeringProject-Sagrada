package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class ChosenWindowPatternCard extends ClientToServerCommand{

    /**
     * Contains the message with chosen WindowPatternCard name
     */
    public ChosenWindowPatternCard(String message) {
        this.message = message;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }
}





