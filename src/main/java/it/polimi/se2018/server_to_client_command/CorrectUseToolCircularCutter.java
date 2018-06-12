package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class CorrectUseToolCircularCutter extends ServerToClientCommand {
    /**
     * True if i want to flip the diem, false if i want to roll it again
     */

    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public CorrectUseToolCircularCutter(String cardName) {
        this.cardName = cardName;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
