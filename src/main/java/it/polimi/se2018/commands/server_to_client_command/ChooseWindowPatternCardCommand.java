package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class ChooseWindowPatternCardCommand extends ServerToClientCommand{
/**
 * Request of ToolCard
 * String with only NameClass
 */
    /**
     * Contains nameClass + three names of Toolcards
     */

    public ChooseWindowPatternCardCommand(String message) {
        this.message = message;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
