package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolFirmPastryBrush2 extends ServerToClientCommand{
    /**
     * // OK SPECIALE
     * WITH THIS I HAVE TO GIVE THE PLAYER the new value
     * he show it and then:
     *
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *
     *  SECOND RESPONSE:
     *  The server accepts the move, and performs it in the Model of the server (following the user first response)
     *
     *  the string is
     *  Integer diePosition
     */

    /**
     * Contains NameClass dieColor SCHEMA/DRAFTPOOL
     */
    private String message;

    private Integer dieValue;

    private Integer diePosition; //in DraftPool or Schema
}
