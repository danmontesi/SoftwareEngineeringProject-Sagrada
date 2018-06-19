package it.polimi.se2018.view.GUI.Notifiers;

import java.util.Observable;

public class RankingPaneNotifier extends Observable {
    private RankingPaneNotifier() {}

    private static class RankingPaneNotifierHolder {
        private static final RankingPaneNotifier INSTANCE = new RankingPaneNotifier();
    }

    public static RankingPaneNotifier getInstance() {
        return RankingPaneNotifierHolder.INSTANCE;
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }
}