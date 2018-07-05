package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public class InvalidAction implements GameBoardAction {
    private String message;

    /**
     * Passes on invalid action message
     */
    public InvalidAction(String message) {
        this.message = message;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public String getMessage() {
        return message;
    }
}
