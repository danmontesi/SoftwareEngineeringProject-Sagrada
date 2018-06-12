package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class CorrectUseToolMoveDieNoRestriction extends ServerToClientCommand{
    /**
     * If true -> value restriction in placement
     * if False -> color restriction in placement
     */


    /**
     * Move a die in your schema without color restrictions
     *
     * As a normal move with 2 positions
     *
     * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer currentPosition(from 0 to 20)
     *
     * Integer nextPosition
     */

    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public CorrectUseToolMoveDieNoRestriction(String cardName) {
        this.cardName = cardName;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
