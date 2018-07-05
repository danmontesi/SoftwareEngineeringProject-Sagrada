package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

import java.io.Serializable;

/**
 * This is the abstract class representing all possible command from Server to Client
 * @author Daniele Montesi
 */
public abstract class ServerToClientCommand implements Serializable {

    protected String message;

    private static final long serialVersionUID = -6460847901998831472L;

    public boolean hasMessage() { return message!=null; }

    public String getMessage() {
        return message;
    }

    /**
     * Visitor method, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public abstract void visit(ControllerClientInterface clientController);
}



