package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class UseToolFirmPastryBrush extends ClientToServerCommand{
    public Integer getDieValue() {
        return dieValue;
    }

    public Integer getDieSchemaPosition() {
        return dieSchemaPosition;
    }

    public Integer getDieDraftpoolPosition() {
        return dieDraftpoolPosition;
    } //TODO perchè non considerato?

    /**
     * Decides the die of DraftPool he wants to roll again
     * message = .. dieColor
     *
     * PENNELLO PASTA SALDA
     *
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *  Integer diePosition
     * Integer dieValue
     *
     * I HAVE TO COMUNICATE TO THE SERVER
     * - WHere i want to place the die
     * - in which position
     */
    //nameClass DRAFTPOOL/SCHEMA dieColor


    private Integer dieValue;

    private Integer dieSchemaPosition; // in  Schema ony ( can be null)

    private Integer dieDraftpoolPosition;

    public UseToolFirmPastryBrush(String message, Integer dieValue, Integer dieDraftpoolPosition, Integer dieSchemaPosition) {
        this.message = message; //this variable will contain the preferred action: DRAFTPOOL if the player want to leave the die in draftpool,or MOVE to perform a move with that die
        this.dieValue = dieValue;
        this.dieSchemaPosition = dieSchemaPosition;
        this.dieDraftpoolPosition = dieDraftpoolPosition;
    }
    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}