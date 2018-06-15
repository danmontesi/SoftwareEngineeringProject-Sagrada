package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class CorrectUseToolWheelsPincher extends ServerToClientCommand{
    /**
     * Muovi 2 dadi in uno stesso turno
     * String that let you do 2 dice moves, and let you skip the turn
     *
     * is like 2 moves
     *
     * String message = "UseToolWheelsPincher dieColor1 dieColor2
     * Integer dievalue1
     * Integer diePosition1(from 0 to 20)
     *
     * Integer dievalue2
     * Integer diePosition2(from 0 to 20)
     */

    private Integer dieValue1;

    private Integer dieSchemaPosition1;

    private Integer dieSchemaPosition2;

    private Integer dieRoundTrackPosition2;

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
