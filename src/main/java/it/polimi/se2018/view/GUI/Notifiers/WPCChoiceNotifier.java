package it.polimi.se2018.view.GUI.Notifiers;

import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardAction;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoiceAction;

import java.util.Observable;

public class WPCChoiceNotifier extends Observable {
    private WPCChoiceNotifier() {}

    private static class WPCChoiceNotifierHolder {
        private static final WPCChoiceNotifier INSTANCE = new WPCChoiceNotifier();
    }

    public static WPCChoiceNotifier getInstance() {
        return WPCChoiceNotifierHolder.INSTANCE;
    }

    public void updateGui(String wpCards) {
        setChanged();
        notifyObservers(wpCards);
    }

    public void updateGui(WPCChoiceAction guiReply) {
        setChanged();
        notifyObservers(guiReply);
    }
}
