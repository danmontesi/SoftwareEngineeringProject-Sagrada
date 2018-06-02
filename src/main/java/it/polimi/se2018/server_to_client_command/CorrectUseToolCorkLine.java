package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolCorkLine extends ServerToClientCommand{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;
}
