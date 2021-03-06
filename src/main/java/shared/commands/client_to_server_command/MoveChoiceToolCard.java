package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class MoveChoiceToolCard  extends ClientToServerCommand{
    private int numberChosen;

    /**
     * Contains the number of the chosen Tool Card, relative to the ordered extracted Tool Cards in the Controller
     */
    public MoveChoiceToolCard(int numberChosen){
        this.numberChosen = numberChosen;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }

    public int getNumberChosen() {
        return numberChosen;
    }
}
