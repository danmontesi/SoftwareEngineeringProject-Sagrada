package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolEglomiseBrush extends ServerToClientCommand{

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

    private String message;

    private Integer dieValue;

    private Integer currentPosition;

    private Integer nextPosition;

}
