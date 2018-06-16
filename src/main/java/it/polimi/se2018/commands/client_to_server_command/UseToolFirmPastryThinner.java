package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

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

    public Integer getDieNewValue() {
        return dieNewValue;
    }

    public Integer getDieOldPosition() {
        return dieOldPosition;
    }

    public Integer getDiePosition() {
        return diePosition;
    }

    /**
     * Contains nameClass DRAFTPOOL/SCHEMA dieColor
     */

    private Integer dieNewValue;

    private Integer dieOldPosition;

    private Integer diePosition; //DraftPool/Schema

    public UseToolFirmPastryThinner(String message, Integer dieNewValue, Integer dieOldPosition, Integer diePosition) {
        this.message = message;
        this.dieNewValue = dieNewValue;
        this.diePosition = diePosition;
        this.dieOldPosition = dieOldPosition;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
