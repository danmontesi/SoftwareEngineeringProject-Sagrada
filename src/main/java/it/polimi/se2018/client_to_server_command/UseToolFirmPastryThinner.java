package it.polimi.se2018.client_to_server_command;

public class UseToolFirmPastryThinner extends ClientToServerCommand{
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

    /**
     * Contains nameClass DRAFTPOOL/SCHEMA dieColor
     */
    private String message;

    private Integer dieNewValue;

    private Integer diePosition; //DraftPool/Schema

    public UseToolFirmPastryThinner(String message, Integer dieNewValue, Integer diePosition) {
        this.message = message;
        this.dieNewValue = dieNewValue;
        this.diePosition = diePosition;
    }
}
