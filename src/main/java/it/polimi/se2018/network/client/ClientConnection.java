package client;

import commands.ServerToClientCommand;

public interface ClientConnection {
    void notifyClient(ServerToClientCommand command);
}
