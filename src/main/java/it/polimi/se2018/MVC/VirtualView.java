package it.polimi.se2018.MVC;

import it.polimi.se2018.Die;
import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.toolcards.ToolCard;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
import java.util.Set;

/**
 *
 * Class for showing to the Client View information about model changes
 *
 * */

public class VirtualView implements Observer{

    //List of connected clients that have to be updated
    private ArrayList<ClientConnection> connectedClients;

    //List of disconnected clients, haven't to be updated. (until they reconnect)
    private ArrayList<ClientConnection> disconnectedClients;

    public VirtualView(ArrayList<ClientConnection> connections) {
        this.connectedClients = connections;
    }

    @Override
    public void update(java.util.Observable model, Object modell) {
        // send refreshCommand
        for (ClientConnection c : connectedClients){
            // send... new RefreshBoardCommand(model);
        }
    }

    public void updateDisconnectedClient(ClientConnection c){
        connectedClients.remove(c);
        disconnectedClients.add(c);
    }
}