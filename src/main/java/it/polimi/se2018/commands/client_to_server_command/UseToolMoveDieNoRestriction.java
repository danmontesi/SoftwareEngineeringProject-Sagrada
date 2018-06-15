package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolMoveDieNoRestriction extends ClientToServerCommand{
    public String getCardName() {
        return cardName;
    }

    public Integer getSchemaOldPosition() {
        return schemaOldPosition;
    }

    public Integer getSchemaNewPosition() {
        return schemaNewPosition;
    }

    /**
     * This class contains the information to send to the Server
     * 1 private string cardName: Distinguish if the card is Eglomise (no color) or Alesatore (value)
     */
    private String cardName;

    private Integer schemaOldPosition;

    private Integer schemaNewPosition; //Can be null or not. If not null -> player can't place any die

    /**
     * Constructor
     * @param cardName
     * @param schemaOldPosition
     * @param schemaNewPosition
     */
    public UseToolMoveDieNoRestriction(String cardName, Integer schemaOldPosition, Integer schemaNewPosition) {
        this.cardName = cardName;
        this.schemaOldPosition = schemaOldPosition;
        this.schemaNewPosition = schemaNewPosition;
    }
    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }

}
