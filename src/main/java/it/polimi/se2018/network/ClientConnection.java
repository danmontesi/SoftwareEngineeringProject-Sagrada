package it.polimi.se2018.network;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

/**
 * Interface for connection
 * can me SocketConnection or RMIConnection
 */
public interface ClientConnection {

    void run();

    void sendCommand(ClientToServerCommand command);
}
