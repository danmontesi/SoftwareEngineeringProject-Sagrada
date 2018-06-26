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

    private boolean increase; //ONLY for Roughing forceps

    private Integer draftPoolPosition;

    /**
     * For increase/decrease tool
     * @param cardName
     * @param draftPoolPosition
     * @param increase
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition, boolean increase) {
        this.cardName = cardName;
        this.increase = increase;
        this.draftPoolPosition = draftPoolPosition;
    }

    /**
     * For flipDie tool
     * @param cardName
     * @param draftPoolPosition
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition) {
        this.cardName = cardName;
        this.draftPoolPosition = draftPoolPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
