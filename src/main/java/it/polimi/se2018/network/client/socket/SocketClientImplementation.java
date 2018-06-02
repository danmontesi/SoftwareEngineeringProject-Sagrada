package client.socket;

import client.ClientConnection;
import commands.ServerToClientCommand;

public class SocketClientImplementation implements ClientConnection {

    @Override
    public void notifyClient(ServerToClientCommand command) {
        System.out.println("Comando ricevuto " + command.toString());
    }
}
