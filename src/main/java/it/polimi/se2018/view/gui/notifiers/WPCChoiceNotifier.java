package it.polimi.se2018.view.gui.notifiers;

import it.polimi.se2018.view.gui.notifiers.wpcchoiceactions.WPCChoiceAction;

import java.util.Observable;

public class WPCChoiceNotifier extends Observable {
    private WPCChoiceNotifier() {}

    private static class WPCChoiceNotifierHolder {
        private static final WPCChoiceNotifier INSTANCE = new WPCChoiceNotifier();
    }

    public static WPCChoiceNotifier getInstance() {
        return WPCChoiceNotifierHolder.INSTANCE;
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }

    public void updateGui(WPCChoiceAction guiReply) {
        setChanged();
        notifyObservers(guiReply);
    }
}
