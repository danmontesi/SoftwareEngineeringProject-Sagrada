package it.polimi.se2018.view.gui.Notifiers;

import java.util.Observable;

public class LobbyNotifier extends Observable {
    private LobbyNotifier() {}

    private static class LobbyNotifierHolder {
        private static final LobbyNotifier INSTANCE = new LobbyNotifier();
    }

    public static LobbyNotifier getInstance() {
        return LobbyNotifierHolder.INSTANCE;
    }

    public void updateGui(String player) {
        setChanged();
        notifyObservers(player);
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }
}
