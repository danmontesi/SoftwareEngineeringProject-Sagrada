package it.polimi.se2018.server_to_client_command;

public class InvalidUseToolFirmPastryBrush1 extends ServerToClientCommand{
    /**
     * Decides the die he wants to change the value
     * message = .. dieColor
     *
     * Integer dieValue
     */

    private Integer dieValue;

    public InvalidUseToolFirmPastryBrush1(String message, Integer dieValue) {
        this.dieValue = dieValue;
        this.message = message;
    }
}
