package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

public class PlayerDisconnectionNotification extends ServerToClientCommand {
    private String username;

    /**
     * Notifies all the other users about the disconnection of one player
     * @param username player's username
     */
    public PlayerDisconnectionNotification(String username) {
        this.username = username;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public String getUsername() {
        return username;
    }
}
