package it.polimi.se2018.client_to_server_command;

public class UseToolEglomiseBrush extends ClientToServerCommand{
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


    public UseToolEglomiseBrush(String message, Integer dieValue, Integer currentPosition, Integer nextPosition) {
        this.message = message;
        this.dieValue = dieValue;
        this.currentPosition = currentPosition;
        this.nextPosition = nextPosition;
    }
}
