package it.polimi.se2018.client_to_server_command;

public class UseToolGavel extends ClientToServerCommand{
    /**
     * Message contains just message= UseToolGavel
     */

    private String message;

    public UseToolGavel(String message) {
        this.message = message;
    }
}
