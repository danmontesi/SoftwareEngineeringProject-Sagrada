package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class ReplyAnotherAction extends ClientToServerCommand{
    private boolean another;

    /**
     * Notifies the Controller about whether the user wants to perform another action (during Tool Card use)
     * @param another true if the user wants to perform another action, false otherwise
     */
    public ReplyAnotherAction(boolean another) {
        this.another = another;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

    public boolean isAnother() {
        return another;
    }
}
