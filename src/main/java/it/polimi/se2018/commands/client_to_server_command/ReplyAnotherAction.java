package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

public class ReplyAnotherAction extends ClientToServerCommand{

    public ReplyAnotherAction(boolean another) {
        this.another = another;
    }

    public boolean isAnother() {
        return another;
    }

    private boolean another;

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

}
