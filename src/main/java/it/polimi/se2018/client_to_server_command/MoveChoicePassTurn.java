package it.polimi.se2018.client_to_server_command;

public class MoveChoicePassTurn extends ClientToServerCommand{


    /**
     * Move performed in case there is no way to place a correct die in the wpc
     */
    private String message;

    public MoveChoicePassTurn(String message) {
        this.message = message;
    }
}
