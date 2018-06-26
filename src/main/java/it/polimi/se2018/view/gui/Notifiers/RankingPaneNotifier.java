package it.polimi.se2018.view.gui.Notifiers;

import java.util.ArrayList;
import java.util.Observable;

public class RankingPaneNotifier extends Observable {
    private boolean open;

    private RankingPaneNotifier() {
        open = false;
    }

    private static class RankingPaneNotifierHolder {
        private static final RankingPaneNotifier INSTANCE = new RankingPaneNotifier();
    }

    public static RankingPaneNotifier getInstance() {
        return RankingPaneNotifierHolder.INSTANCE;
    }

    public void updateGui(ArrayList<String> scores) {
        setChanged();
        notifyObservers(scores);
    }

    public void setOpen() {
        this.open=true;
    }

    public boolean isOpen() {
        return open;
    }
}