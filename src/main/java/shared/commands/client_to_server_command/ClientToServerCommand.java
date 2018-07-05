package shared.commands.client_to_server_command;

import shared.utils.ControllerServerInterface;

import java.io.Serializable;

/**
 * This is the abstract class representing all possible commands from Client to Server
 * @author Daniele Montesi
 */
public abstract class ClientToServerCommand implements Serializable {

    protected String message;

    protected String username;

    private static final long serialVersionUID = -6460847931998831472L;

    public String getMessage(){
        return message;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasUsername(){
        return username != null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract void visit(ControllerServerInterface observer);
}
