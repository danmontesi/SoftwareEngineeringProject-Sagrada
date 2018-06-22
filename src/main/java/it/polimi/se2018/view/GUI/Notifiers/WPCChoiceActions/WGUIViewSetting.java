package it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions;

import it.polimi.se2018.view.GUI.GUIView;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardAction;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardVisitor;

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
