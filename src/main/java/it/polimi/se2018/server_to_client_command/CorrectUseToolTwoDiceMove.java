package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class CorrectUseToolTwoDiceMove extends ServerToClientCommand {

    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public CorrectUseToolTwoDiceMove(String cardName) {
        this.cardName = cardName;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
