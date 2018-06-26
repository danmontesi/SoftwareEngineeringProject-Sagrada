package it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions;

import it.polimi.se2018.view.gui.GUIView;

public class WGUIViewSetting implements WPCChoiceAction {
    private GUIView guiView;

    public WGUIViewSetting(GUIView guiView) {
        this.guiView = guiView;
    }

    @Override
    public void acceptWPCChoiceVisitor(WPCChoiceVisitor wpcChoiceVisitor) {
        wpcChoiceVisitor.visitWPCChoiceAction(this);
    }

    public GUIView getGuiView() {
        return guiView;
    }

}
