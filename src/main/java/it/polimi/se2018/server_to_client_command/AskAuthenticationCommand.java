package it.polimi.se2018.server_to_client_command;

public class AskAuthenticationCommand{
    /**
     * Send a request for a new Username (probably because the one chosen is incorrect)
     * String that cointains NameClass
     */
    private String message;

    public AskAuthenticationCommand(String message) {
        this.message = message;
    }
}
