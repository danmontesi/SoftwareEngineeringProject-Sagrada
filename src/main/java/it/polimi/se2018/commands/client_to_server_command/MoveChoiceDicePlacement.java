package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

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

    private Integer dieSchemaColPosition;

    private Integer dieSchemaRowPosition;

    private Integer dieDraftPoolPosition;

    public MoveChoiceDicePlacement(String message, Integer dieSchemaRowPosition, Integer dieSchemaColPosition,Integer dieDraftPoolPosition) {
        this.message = message;
        this.dieSchemaRowPosition = dieSchemaRowPosition;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
        this.dieSchemaColPosition = dieSchemaColPosition;
    }
    public Integer getDieSchemaRowPosition() {
        return dieSchemaRowPosition;
    }

    public Integer getDieSchemaColPosition() {
        return dieSchemaColPosition;
    }

    public Integer getDieDraftPoolPosition() {
        return dieDraftPoolPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
