package it.polimi.se2018.view.gui.Notifiers;

import java.util.List;
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

    public void updateGui(List<String> scores) {
        setChanged();
        notifyObservers(scores);
    }

    public void setOpen(boolean b) {
        if (b) {
            this.open = true;
        } else {
            this.open = false;
        }
    }

    public boolean isOpen() {
        return open;
    }
}