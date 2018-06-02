package it.polimi.se2018.server_to_client_command;

public class AuthenticatedCorrectlyCommand extends ServerToClientCommand{
/**
 * Comunicates that the username is correct
 * String with only NameClass
 */
private String message;

    public AuthenticatedCorrectlyCommand(String message) {
        this.message = message;
    }
}
