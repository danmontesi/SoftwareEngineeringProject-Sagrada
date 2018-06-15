package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolTwoDicePlacement extends ClientToServerCommand {

    public String getCardName() {
        return cardName;
    }

    public Integer getSchemaOldPosition1() {
        return schemaOldPosition1;
    }

    public Integer getSchemaNewPosition1() {
        return schemaNewPosition1;
    }

    public Integer getSchemaOldPosition2() {
        return schemaOldPosition2;
    }

    public Integer getSchemaNewPosition2() {
        return schemaNewPosition2;
    }

    /**
     * This class contains the information to send to the Server
     * 1 private string cardName: Distinguish if the card is ManualCutter or Lathekin (With color of roundTrack)
     */
    private String cardName;

    private Integer schemaOldPosition1;

    private Integer schemaNewPosition1;

    private Integer schemaOldPosition2;

    private Integer schemaNewPosition2;

    public UseToolTwoDicePlacement(String cardName, Integer schemaOldPosition1, Integer schemaNewPosition1, Integer schemaOldPosition2, Integer schemaNewPosition2) {
        this.cardName = cardName;
        this.schemaOldPosition1 = schemaOldPosition1;
        this.schemaNewPosition1 = schemaNewPosition1;
        this.schemaOldPosition2 = schemaOldPosition2;
        this.schemaNewPosition2 = schemaNewPosition2;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
