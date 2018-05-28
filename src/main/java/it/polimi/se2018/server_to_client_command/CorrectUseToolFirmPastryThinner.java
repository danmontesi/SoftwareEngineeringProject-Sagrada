package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolFirmPastryThinner extends ServerToClientCommand{
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
     */
    private String message;

    private Integer dieNewValue;

    private Integer diePosition;
}
