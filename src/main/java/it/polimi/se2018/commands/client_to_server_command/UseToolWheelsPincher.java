package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolWheelsPincher extends ClientToServerCommand{
    public Integer getDieDraftPoolPosition() {
        return dieDraftPoolPosition;
    }

    public Integer getDieSchemaPosition() {
        return dieSchemaPosition;
    }
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

    private Integer dieDraftPoolPosition;

    private Integer dieSchemaPosition;

    public UseToolWheelsPincher(Integer dieDraftPoolPosition, Integer dieSchemaPosition) {
        this.dieDraftPoolPosition = dieDraftPoolPosition;
        this.dieSchemaPosition = dieSchemaPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
