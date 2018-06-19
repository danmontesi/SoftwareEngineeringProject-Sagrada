package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolChangeDieValue extends ClientToServerCommand {

    /**
     * This class contains the information to send to the Server
     * 1 private string cardName: Distinguish if the card is Roughing (aumenta/dim) or Diamond (flip)
     */
    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public boolean isIncrease() {
        return increase;
    }

    public Integer getDraftPoolPosition() {
        return draftPoolPosition;
    }

    public Integer getSchemaPosition() {
        return schemaPosition;
    }

    private boolean increase; //ONLY for Roughing forceps

    private Integer draftPoolPosition;

    private Integer schemaPosition;

    /**
     * For increase/decrease tool
     * @param cardName
     * @param draftPoolPosition
     * @param schemaPosition
     * @param increase
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition, Integer schemaPosition, boolean increase) {
        this.cardName = cardName;
        this.increase = increase;
        this.draftPoolPosition = draftPoolPosition;
        this.schemaPosition = schemaPosition;
    }

    /**
     * For flipDie tool
     * @param cardName
     * @param draftPoolPosition
     * @param schemaPosition
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition, Integer schemaPosition) {
        this.cardName = cardName;
        this.draftPoolPosition = draftPoolPosition;
        this.schemaPosition = schemaPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
