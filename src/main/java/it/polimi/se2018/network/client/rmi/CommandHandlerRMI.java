package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.network.server.ServerConnection;

public class CommandHandlerRMI extends Thread {

    public boolean active;
    private RMIClient connection;

    public CommandHandlerRMI(RMIClient connection) {
        this.connection = connection;
        active=true;
    }

    @Override
    public void run() {
        while (active) {
            if (!RMIClient.getCommandQueue().isEmpty()) {
                System.out.println("Sono fuori");
                System.out.println("Sono vivo");
                connection.getClientController().dispatchCommand(RMIClient.getCommandQueue().poll());
            }
        }
    }
}
