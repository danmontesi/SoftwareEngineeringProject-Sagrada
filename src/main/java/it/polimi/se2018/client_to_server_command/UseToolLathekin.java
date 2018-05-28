package it.polimi.se2018.client_to_server_command;

public class UseToolLathekin extends ClientToServerCommand{
    /**
     * Tool 9
     * Exactly as a normal move, String contains just the Name
     * MoveChoiceSimpleDicePlacement diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     */

    private String message;

    private Integer dieOldPosition1;

    private Integer dieOldPosition2;

    private Integer dieNewPosition1;

    private Integer dieNewPosition2;

    public UseToolLathekin(String message, Integer dieOldPosition1, Integer dieOldPosition2, Integer dieNewPosition1, Integer dieNewPosition2) {
        this.message = message;
        this.dieOldPosition1 = dieOldPosition1;
        this.dieOldPosition2 = dieOldPosition2;
        this.dieNewPosition1 = dieNewPosition1;
        this.dieNewPosition2 = dieNewPosition2;
    }
}
