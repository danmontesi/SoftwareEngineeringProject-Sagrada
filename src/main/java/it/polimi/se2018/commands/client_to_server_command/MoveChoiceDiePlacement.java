package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class MoveChoiceDiePlacement extends ClientToServerCommand{
    private Integer dieDraftPoolPosition;
    private Integer dieSchemaPosition;

    /**
     * Contains the message with performed regular move
     * @param dieSchemaPosition destination cell in the Window Pattern Card
     * @param dieDraftPoolPosition source cell in the Draft Pool
     */
    public MoveChoiceDiePlacement(Integer dieSchemaPosition, Integer dieDraftPoolPosition) {
        this.dieSchemaPosition = dieSchemaPosition ;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }

    public Integer getDieDraftPoolPosition() {
        return dieDraftPoolPosition;
    }

    public Integer getDieSchemaPosition() {
        return dieSchemaPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
