package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

import it.polimi.se2018.view.gui.GUIView;

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
