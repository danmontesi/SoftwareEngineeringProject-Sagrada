package it.polimi.se2018.network.rmi;

import it.polimi.se2018.MVC.ClientNetworkHandler;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.ClientConnection;

import java.io.BufferedReader;
import java.rmi.registry.Registry;


// TODO PER ALE: implementa RMIClient. Però, essendo figlia di ClientConnection, e visto che il socket si chiama ClientConnectionSocket, la lascerei chiamata ClientConnectionRMI per semplicità
public class ClientConnectionRMI extends ClientConnection {

    private BufferedReader reader;
    private ClientNetworkHandler client;
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
