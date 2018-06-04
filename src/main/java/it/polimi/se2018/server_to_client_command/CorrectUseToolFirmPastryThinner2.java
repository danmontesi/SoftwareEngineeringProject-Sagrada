package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolFirmPastryThinner2 extends ServerToClientCommand{
    /**
     *
     * I HAVE TO GIVE THE PLAYER THE DIE PICKED FROM DICEBAG
     * String dieColor
     *
     * Integer dieValue
     * then->
     * Here, the player decides the value of the picked Die
     *
     * Message = UseToolFirmyPastryThinner2
     * Integer newValue
     *
     * Integer position(from 0 to 20)
     *
     *
     * Server receives the new value, when he want to put the die, and also the chosen die to put into the dicebag!
     *
     * RESPONSE:
     * Accept the move and do the changes to the Server Model
     *
     *
     */

    /**
     *
     */
    private Integer dieValue;

    private Integer diePosition; // in Draftpool or Schema
}
