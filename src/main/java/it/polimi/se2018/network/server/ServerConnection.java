package it.polimi.se2018.network.server;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

public interface ServerConnection {
    void send(ClientToServerCommand command);
    void startConnection();
}
