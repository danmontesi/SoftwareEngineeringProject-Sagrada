package server;

import commands.ClientToServerCommand;

public interface ServerConnection {
    void send(ClientToServerCommand command);
    void startConnection();
}
