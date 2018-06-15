package it.polimi.se2018.commands.client_to_server_command;

public class UseToolWheelsPincher extends ClientToServerCommand{
    public Integer getDieDraftPoolPosition1() {
        return dieDraftPoolPosition1;
    }

    public Integer getDieSchemaPosition1() {
        return dieSchemaPosition1;
    }

    public Integer getDieSchemaPosition2() {
        return dieSchemaPosition2;
    }

    public Integer getDieDraftPoolPosition2() {
        return dieDraftPoolPosition2;
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

    private Integer dieDraftPoolPosition1;

    private Integer dieSchemaPosition1;

    private Integer dieSchemaPosition2;

    private Integer dieDraftPoolPosition2;

    public UseToolWheelsPincher(String message, Integer dieDraftPoolPosition1, Integer dieSchemaPosition1, Integer dieSchemaPosition2, Integer dieDraftPoolPosition2) {
        this.message = message;
        this.dieDraftPoolPosition1 = dieDraftPoolPosition1;
        this.dieSchemaPosition1 = dieSchemaPosition1;
        this.dieSchemaPosition2 = dieSchemaPosition2;
        this.dieDraftPoolPosition2 = dieDraftPoolPosition2;
    }
}
