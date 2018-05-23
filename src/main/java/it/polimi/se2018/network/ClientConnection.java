package it.polimi.se2018.network;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

/**
 * Interface for connection
 * can me SocketConnection or RMIConnection
 */
public abstract class ClientConnection implements Runnable{

    public void run(){

    };

    public void sendCommand(ClientToServerCommand command){

    };

    public void startThread(){

    }
}
