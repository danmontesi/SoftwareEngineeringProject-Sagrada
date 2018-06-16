package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class NewConnectedPlayerNotification extends ServerToClientCommand {

    private String username;

    public String getUsername() {
        return username;
    }

    public NewConnectedPlayerNotification(String username) {

        this.username = username;
    }

    @Override
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
