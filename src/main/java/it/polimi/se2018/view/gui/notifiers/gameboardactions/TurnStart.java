package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public class TurnStart implements GameBoardAction {
    private String username;

    public TurnStart(String username) {
        this.username = username;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public String getUsername() {
        return username;
    }
}
