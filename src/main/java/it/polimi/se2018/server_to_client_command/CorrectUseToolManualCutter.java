package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolManualCutter extends ServerToClientCommand{
    /**
     * Tool that moves 2 same color dice
     *
     * message = UseToolManualCutter dieColor
     *
     * Integer die1CurrentPosition (in the schema)
     * Integer die2CurrentPosition (in the schema)
     *
     * Integer dieNextPosition1
     * Integer dieNextPosition2
     */

    private String message;

    private Integer dieNextPosition2;

    private Integer dieNextPosition1;

    private Integer dieCurrentPosition1;

    private Integer dieCurrentPosition2;
}
