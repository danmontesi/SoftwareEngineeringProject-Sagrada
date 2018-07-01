package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public interface GameBoardVisitor {
    void visitGameBoardAction(GUIViewSetting guiViewSetting);
    void visitGameBoardAction(RefreshBoard refreshBoard);
    void visitGameBoardAction(TurnStart turnStart);
    void visitGameBoardAction(TurnUpdate turnUpdate);
    void visitGameBoardAction(InvalidAction invalidAction);
    void visitGameBoardAction(WPCUpdate wpcUpdate);
    void visitGameBoardAction(TokensUpdate tokensUpdate);
    void visitGameBoardAction(DraftPoolRoundTrackUpdate draftPoolUpdate);
    void visitGameBoardAction(AnotherAction anotherAction);
    void visitGameBoardAction(IncreaseDecrease increaseDecrease);
    void visitGameBoardAction(DieValue dieValue);
    void visitGameBoardAction(PlaceDie placeDie);
    void visitGameBoardAction(PickDie pickDie);
    void visitGameBoardAction(TimeUp timeUp);
    void visitGameBoardAction(Message message);
}
