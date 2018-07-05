package it.polimi.se2018.view.gui.notifiers.gameboardactions;

/**
 * This is the interface representing all possible commands from GuiView to GameBoardController
 * @author Nives Migotto
 */
public interface GameBoardAction {
    void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor);
}
