package it.polimi.se2018.network;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

import java.util.Observable;
import java.util.Observer;

/**
 * Interface for connection
 * can me SocketConnection or RMIConnection
 */
public abstract class ClientConnection implements Runnable, Observer{

    public void run(){

    };

    public void sendCommand(ClientToServerCommand command){

    };

    public void startThread(){

    }

    public void update(Observable o, Object obj){

    }
}
