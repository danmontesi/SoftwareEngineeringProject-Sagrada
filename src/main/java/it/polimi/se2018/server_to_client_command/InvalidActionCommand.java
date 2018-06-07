package it.polimi.se2018.server_to_client_command;

public class InvalidActionCommand extends ServerToClientCommand{
    public InvalidActionCommand(String message) {
        this.message= message;
    }
}
