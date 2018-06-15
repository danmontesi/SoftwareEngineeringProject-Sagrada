package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class CorrectUseToolCorkLine extends ServerToClientCommand{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private Integer dieValue;

    private Integer dieRoundTrackPosition;

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
