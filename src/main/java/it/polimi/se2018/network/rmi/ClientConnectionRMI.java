package it.polimi.se2018.network.rmi;

import it.polimi.se2018.MVC.ClientController;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.ClientConnection;

import java.io.BufferedReader;
import java.rmi.registry.Registry;

public class ClientConnectionRMI implements ClientConnection {

    private BufferedReader reader;
    private ClientController client;
    private String clientName;
    private Registry serverConnectionRegistry;
    private Registry clientRegistry;

    @Override
    public void run() {

    }

    @Override
    public void sendCommand(ClientToServerCommand command) {

    }
    //...


    //TODO guarda Clientinterface
}
