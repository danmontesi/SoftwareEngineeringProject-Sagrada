package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class NewConnectedPlayerNotification extends ServerToClientCommand {
    private String username;

    /**
     * Notifies all the other users about the connection of one player
     */
    public NewConnectedPlayerNotification(String username) {
        this.username = username;
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public String getUsername() {
        return username;
    }
}
