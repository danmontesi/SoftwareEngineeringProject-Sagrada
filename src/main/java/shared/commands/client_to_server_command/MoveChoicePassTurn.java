package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class MoveChoicePassTurn extends ClientToServerCommand{
    /**
     * Move performed when the user wants to pass
     */
    public MoveChoicePassTurn() {}

    public MoveChoicePassTurn(String username) {
        this.username = username;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
