package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

public class UndoActionCommand extends ClientToServerCommand {
    /**
     * Move performed when the user wants to undo the last action he performed
     */
    public UndoActionCommand() {}

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }
}
