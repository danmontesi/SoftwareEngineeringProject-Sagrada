package client.view.gui.notifiers;

import client.view.gui.notifiers.wpcchoiceactions.WPCChoiceAction;

import java.util.Observable;

public class WPCChoiceNotifier extends Observable {
    private boolean open;

    private WPCChoiceNotifier() {
        open = false;
    }

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
