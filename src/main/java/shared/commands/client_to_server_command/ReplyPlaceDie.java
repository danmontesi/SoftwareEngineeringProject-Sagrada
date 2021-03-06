package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class ReplyPlaceDie extends ClientToServerCommand {
    private Integer position;

    /**
     * Contains the cell the user wants to select (in order to place a die on it)
     */
    public ReplyPlaceDie(Integer position) {
        this.position = position;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

    public Integer getPosition() {
        return position;
    }
}
