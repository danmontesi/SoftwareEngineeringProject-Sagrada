package it.polimi.se2018.client_to_server_command;

public class UseToolFirmPastryBrush extends ClientToServerCommand{
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

    private String message; //nameClass DRAFTPOOL/SCHEMA dieColor

    private Integer dieValue;

    private Integer diePosition; // in Draftpool or Schema

    public UseToolFirmPastryBrush(String message, Integer dieValue, Integer diePosition) {
        this.message = message;
        this.dieValue = dieValue;
        this.diePosition = diePosition;
    }
}