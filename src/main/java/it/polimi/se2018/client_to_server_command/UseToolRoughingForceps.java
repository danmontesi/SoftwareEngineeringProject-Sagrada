package it.polimi.se2018.client_to_server_command;

public class UseToolRoughingForceps extends ClientToServerCommand{
    public Integer getDiePosition() {
        return diePosition;
    }

    /**
     * Has to chose the die from Draftpool and indicates if to increase/decrease the value
     *
     * Message .. dieColor
     *
     * Integer dieValue
     */

    private Integer diePosition;

    public UseToolRoughingForceps(String message, Integer diePosition) {
        this.message = message;
        this.diePosition = diePosition;
    }
}
