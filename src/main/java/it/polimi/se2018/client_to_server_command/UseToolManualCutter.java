package it.polimi.se2018.client_to_server_command;

public class UseToolManualCutter extends ClientToServerCommand{
    public Integer getDieNextPosition2() {
        return dieNextPosition2;
    }

    public Integer getDieNextPosition1() {
        return dieNextPosition1;
    }

    public Integer getDieCurrentPosition1() {
        return dieCurrentPosition1;
    }

    public Integer getDieCurrentPosition2() {
        return dieCurrentPosition2;
    }

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

    private Integer dieNextPosition2;

    private Integer dieNextPosition1;

    private Integer dieCurrentPosition1;

    private Integer dieCurrentPosition2;

    public UseToolManualCutter(String message, Integer dieNextPosition2, Integer dieNextPosition1, Integer dieCurrentPosition1, Integer dieCurrentPosition2) {
        this.message = message;
        this.dieNextPosition2 = dieNextPosition2;
        this.dieNextPosition1 = dieNextPosition1;
        this.dieCurrentPosition1 = dieCurrentPosition1;
        this.dieCurrentPosition2 = dieCurrentPosition2;
    }

}
