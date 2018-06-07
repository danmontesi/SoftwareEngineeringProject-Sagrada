package it.polimi.se2018.server_to_client_command;

public class AuthenticatedCorrectlyCommand extends ServerToClientCommand{
/**
 * Communicates that the username is correct
 * String with only NameClass
 */

//MESSAGE contains only the new username
    public AuthenticatedCorrectlyCommand(String message) {
        this.message = message;
    }
}
