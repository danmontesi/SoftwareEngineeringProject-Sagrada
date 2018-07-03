package it.polimi.se2018.network.server;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;

public interface ServerConnection {

    void send(ClientToServerCommand command);

    /* As soon as the connection is set, the player has to specify the username he wants to log in with.
       Username must be unique in all games.
       If you attempt to reconnect with the same username after a disconnection you will join your former game. */

    void startConnection(String username);
}