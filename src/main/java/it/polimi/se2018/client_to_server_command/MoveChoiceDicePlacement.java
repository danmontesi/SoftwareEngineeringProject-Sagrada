package it.polimi.se2018.client_to_server_command;

public class MoveChoiceDicePlacement extends ClientToServerCommand{

    /**
     * Move performed that specifies in a String attribute ordered, the Die type the user want to move (he chose it from the draftPool by clicking it
     * the string will be parsed in this way:
     *
     * MoveChoiceSimpleDicePlacement diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     *
     * e.g.
     * "MoveChoiceSimpleDicePlacement GREEN"
     *
     * Integer dievalue=3
     * Integer dieposition=2 (corrisponde alla riga 0 colonna 2)
     *
     * The controller server-side will check if the die is present in the DraftPool
     */
    private String message;

    private Integer dieSchemaPosition;

    private Integer dieDraftPoolPosition;

    public MoveChoiceDicePlacement(String message, Integer dieSchemaPosition, Integer dieDraftPoolPosition) {
        this.message = message;
        this.dieSchemaPosition = dieSchemaPosition;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }
}
