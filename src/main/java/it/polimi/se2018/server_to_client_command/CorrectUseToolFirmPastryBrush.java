package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolFirmPastryBrush extends ServerToClientCommand{
    /**
     *
     * WITH THIS I HAVE TO GIVE THE PLAYER the new value
     * he show it and then:
     *
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *  Integer diePosition
     */

    private String message;

    private Integer dieRoundTrackPosition;
}
