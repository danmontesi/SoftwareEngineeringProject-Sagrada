package it.polimi.se2018.network.client;

import java.util.Observable;
import java.util.Observer;

public class ClientController implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Ho ricevuto il comando" + arg.toString());
    }
}
