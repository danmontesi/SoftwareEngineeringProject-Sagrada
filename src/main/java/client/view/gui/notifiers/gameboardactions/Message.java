package client.view.gui.notifiers.gameboardactions;

public class Message implements GameBoardAction {
    private String message;

    /**
     * Passes on generic messages
     */
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
