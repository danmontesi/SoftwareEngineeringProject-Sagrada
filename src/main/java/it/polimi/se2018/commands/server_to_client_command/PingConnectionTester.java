package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class PingConnectionTester extends ServerToClientCommand {
    /**
     * Verifies whether a user is still connected or not
     */
    public PingConnectionTester() {
        this.message="Ping";
    }

    public void visit(ControllerClientInterface clientController) {
        System.out.println("Voleeeeevi!");
    }
}
