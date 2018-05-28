package it.polimi.se2018.client_to_server_command;

public class UseToolCorkLine extends ClientToServerCommand{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private String message;

    private Integer schemaPosition;

    private Integer dieDraftPoolPosition;

    public UseToolCorkLine(String message, Integer schemaPosition, Integer dieDraftPoolPosition) {
        this.message = message;
        this.schemaPosition = schemaPosition;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }
}
