package it.polimi.se2018.client_to_server_command;

public class UseToolFirmPastryBrush extends ClientToServerCommand{
    /**
     * Decides the die of DraftPool he wants to roll again
     * message = .. dieColor
     *
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *  Integer diePosition
     * Integer dieValue
     */

    private String message;

    private Integer dieNewValue;

    private Integer dieDraftPoolPosition;

    public UseToolFirmPastryBrush(String message, Integer dieNewValue, Integer dieDraftPoolPosition) {
        this.message = message;
        this.dieNewValue = dieNewValue;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }
}
