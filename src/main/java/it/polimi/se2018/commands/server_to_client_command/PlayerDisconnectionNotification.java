package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class PlayerDisconnectionNotification extends ServerToClientCommand {

    private String username;

    public String getUsername() {
        return username;
    }

    public PlayerDisconnectionNotification(String username) {

        this.username = username;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
