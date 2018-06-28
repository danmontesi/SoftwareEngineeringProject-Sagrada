package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class Message implements GameBoardAction {
    private String message;

    public Message(String message) {
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
