package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

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

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
