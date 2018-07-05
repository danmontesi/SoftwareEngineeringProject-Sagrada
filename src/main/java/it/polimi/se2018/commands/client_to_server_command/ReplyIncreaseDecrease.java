package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyIncreaseDecrease extends ClientToServerCommand {
    private boolean increase;

    /**
     * Notifies the Controller about whether the user decided to increase or decrease the die value
     * @param increase true if the user decided to increase the die value, false otherwise
     */
    public ReplyIncreaseDecrease(boolean increase) {
        this.increase = increase;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

    public boolean isIncrease() {
        return increase;
    }
}
