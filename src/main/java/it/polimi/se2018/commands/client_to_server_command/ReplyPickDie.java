package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyPickDie extends ClientToServerCommand {
    private Integer index;

    /**
     * Contains the die the user wants to pick (in order to place it somewhere else)
     */
    public ReplyPickDie(Integer index) {
        this.index = index;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

    public Integer getIndex() {
        return index;
    }
}
