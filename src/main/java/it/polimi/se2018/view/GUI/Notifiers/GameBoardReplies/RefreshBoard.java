package it.polimi.se2018.view.GUI.Notifiers.GameBoardReplies;

public class RefreshBoard implements GameBoardReply {
    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardReply(this);
    }
}
