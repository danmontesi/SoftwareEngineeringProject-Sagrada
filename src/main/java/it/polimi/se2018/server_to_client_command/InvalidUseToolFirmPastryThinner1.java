package it.polimi.se2018.server_to_client_command;

public class InvalidUseToolFirmPastryThinner1 extends ServerToClientCommand{
    /**
     * Here, the player decides the value of the picked Die
     *
     * Message = UseToolFirmyPastryThinner2
     * Integer newValue
     *
     * Integer position(from 0 to 20)
     *
     */


    private Integer newValue;

    public InvalidUseToolFirmPastryThinner1(String message, Integer newValue) {
        this.newValue = newValue;
        this.message = "InvalidUseToolFirmPastryThinner1" + " " + message;
    }
}
