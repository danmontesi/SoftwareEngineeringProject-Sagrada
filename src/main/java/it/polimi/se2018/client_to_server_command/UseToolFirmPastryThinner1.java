package it.polimi.se2018.client_to_server_command;

public class UseToolFirmPastryThinner1 extends ClientToServerCommand{
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

    public UseToolFirmPastryThinner1(String message) {
        this.message = message;
    }
}
