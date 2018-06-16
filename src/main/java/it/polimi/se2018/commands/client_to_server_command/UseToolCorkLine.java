package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolCorkLine extends ClientToServerCommand{

    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private Integer schemaPosition;

    private Integer dieDraftPoolPosition;

    public UseToolCorkLine(String message, Integer schemaPosition, Integer dieDraftPoolPosition) {
        this.message = message;
        this.schemaPosition = schemaPosition;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getSchemaPosition() {
        return schemaPosition;
    }

    public Integer getDieDraftPoolPosition() {
        return dieDraftPoolPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
