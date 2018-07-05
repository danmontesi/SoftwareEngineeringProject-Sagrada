package it.polimi.se2018.view.gui.notifiers.wpcchoiceactions;

import it.polimi.se2018.view.gui.GUIView;

public class WGUIViewSetting implements WPCChoiceAction {
    private GUIView guiView;

    /**
     * Passes guiView on to WPCChoiceController so that it can interact with guiView
     */
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
