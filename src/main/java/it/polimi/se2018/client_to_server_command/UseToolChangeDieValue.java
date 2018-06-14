package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolChangeDieValue extends ClientToServerCommand {

    /**
     * This class contains the information to send to the Server
     * 1 private string cardName: Distinguish if the card is Roughing (aumenta/dim) or Diamond (flip)
     */
    private String cardName;

    private boolean increase; //ONLY for Roughing forceps

    private boolean placedDie; //Setted depending from the constructor

    private Integer draftPoolPosition;

    private Integer schemaPosition; //Can be null or not. If not null -> player can't place any die

    /**
     * For increase/decrease tool
     * @param cardName
     * @param draftPoolPosition
     * @param schemaPosition
     * @param increase
     * @param placedDie
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition, Integer schemaPosition, boolean increase, boolean placedDie) {
        this.cardName = cardName;
        this.increase = increase;
        this.draftPoolPosition = draftPoolPosition;
        this.schemaPosition = schemaPosition;
        this.placedDie = placedDie;
    }

    /**
     * For flipDie tool
     * @param cardName
     * @param draftPoolPosition
     * @param schemaPosition
     * @param placedDie
     */
    public UseToolChangeDieValue(String cardName, Integer draftPoolPosition, Integer schemaPosition, boolean placedDie) {
        this.cardName = cardName;
        this.draftPoolPosition = draftPoolPosition;
        this.schemaPosition = schemaPosition;
        this.placedDie = placedDie;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
