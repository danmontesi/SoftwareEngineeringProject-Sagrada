package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class ReplyDieValue extends ClientToServerCommand {
    private Integer value;

    /**
     * Contains the chosen die value
     */
    public ReplyDieValue(Integer value) {
        this.value = value;
    }

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(),this);
    }

    public Integer getValue() {
        return value;
    }
}
