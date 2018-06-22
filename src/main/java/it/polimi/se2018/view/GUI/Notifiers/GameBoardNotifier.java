package it.polimi.se2018.view.GUI.Notifiers;

import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardAction;

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

    public void updateGui(GameBoardAction guiReply) {
        setChanged();
        notifyObservers(guiReply);
    }
}