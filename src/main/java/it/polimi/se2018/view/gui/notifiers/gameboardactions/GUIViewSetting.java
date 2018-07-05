package it.polimi.se2018.view.gui.notifiers.gameboardactions;

import it.polimi.se2018.view.gui.GUIView;

public class GUIViewSetting implements GameBoardAction {
    private GUIView guiView;

    /**
     * Passes guiView on to GameBoardController so that it can interact with guiView
     */
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
