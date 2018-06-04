package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolFirmPastryThinner1 extends ServerToClientCommand{
    /**
     * // OK SPECIALE
     *
     * DILUENTE PER PASTA SALDA
     * I HAVE TO GIVE THE PLAYER THE DIE PICKED FROM DICEBAG
     * String dieColor
     *
     * Integer dieValue
     * then->
     * Here, the player decides the value of the picked Die
     *
     * Message = UseToolFirmPastryThinner2 dieColor
     * Integer newValue
     *
     *
     * Server receives JUST the request
     *
     * FIRST RESPONSE:
     * It says ok and gives the player a new die (also without value)
     *
     * Integer dieValue
     *
     */

    /**
     * Contains nameClass + dieColor
     */

    private Integer dieValue;

    public Integer getDieValue() {
        return dieValue;
    }

    public CorrectUseToolFirmPastryThinner1(String message, Integer dieValue) {
        this.message = "CorrectUseToolFirmPastryThinner1 " + message;
        this.dieValue = dieValue;
    }
}
