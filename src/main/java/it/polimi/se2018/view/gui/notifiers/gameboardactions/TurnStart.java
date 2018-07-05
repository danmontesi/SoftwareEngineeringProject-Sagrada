package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public class TurnStart implements GameBoardAction {
    private String username;

    /**
     * Notifies the player of a user's turn start
     * @param username if it is null it is the player's turn, otherwise of another user (corresponding to the username)
     */
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
