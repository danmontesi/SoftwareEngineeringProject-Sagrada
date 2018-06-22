package it.polimi.se2018.view.GUI.Notifiers.GameBoardActions;

import it.polimi.se2018.view.GUI.GUIView;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoiceAction;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions.WPCChoiceVisitor;

public class GUIViewSetting implements GameBoardAction {
    private GUIView guiView;

    public GUIViewSetting(GUIView guiView) {
        this.guiView = guiView;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public GUIView getGuiView() {
        return guiView;
    }

}
