package it.polimi.se2018.client.GUI;

import java.util.List;

public class WPCChoiceNotifier extends java.util.Observable {
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

    public void updateGui() {
        setChanged();
        notifyObservers();
    }
}
