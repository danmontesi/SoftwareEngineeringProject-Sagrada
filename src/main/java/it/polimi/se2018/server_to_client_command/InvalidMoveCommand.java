package it.polimi.se2018.server_to_client_command;

public class InvalidMoveCommand extends ServerToClientCommand{
    /**
     * Move is incorrect-> Notify it to the player and ask him a new choice from turn menu (not obliged to do move, also tool..)
     */

    public InvalidMoveCommand(String message) {
        this.message = message;
    }
}
