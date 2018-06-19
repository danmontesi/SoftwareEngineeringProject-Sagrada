package it.polimi.se2018.client.GUI.Notifiers;

import java.util.Observable;

public class GameBoardNotifier extends Observable {
    private GameBoardNotifier() {}

    private static class GameBoardNotifierHolder {
        private static final GameBoardNotifier INSTANCE = new GameBoardNotifier();
    }

    public static GameBoardNotifier getInstance() {
        return GameBoardNotifierHolder.INSTANCE;
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }
}