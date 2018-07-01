package it.polimi.se2018.view.gui.notifiers;

import java.util.ArrayList;
import java.util.Observable;

public class LobbyNotifier extends Observable {
    private LobbyNotifier() {}

    private static class LobbyNotifierHolder {
        private static final LobbyNotifier INSTANCE = new LobbyNotifier();
    }

    public static LobbyNotifier getInstance() {
        return LobbyNotifierHolder.INSTANCE;
    }

    public void updateGui(String msg, Integer type) {
        setChanged();
        ArrayList<String> message = new ArrayList<>();
        message.add(msg);
        message.add(type.toString());
        notifyObservers(message);
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }
}
