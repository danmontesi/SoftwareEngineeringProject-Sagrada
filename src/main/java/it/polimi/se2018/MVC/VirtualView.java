package it.polimi.se2018.MVC;

import it.polimi.se2018.Die;
import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
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


    private ArrayList<Connection> connections;
    private Model model = null;
    public VirtualView(Model model) {
        this.model = model;
    }

    @Override
    public void update(java.util.Observable model, Object modell) {
        // send refreshCommand
        for (Connection c : connections){
            // send... new RefreshBoardCommand(model);
        }
    }
}