package it.polimi.se2018.view.GUI.Notifiers.GameBoardActions;

public interface GameBoardVisitor {
    void visitGameBoardAction(GUIViewSetting guiViewSetting);
    void visitGameBoardAction(RefreshBoard refreshBoard);
    void visitGameBoardAction(TurnStart turnStart);
    void visitGameBoardAction(TurnUpdate turnUpdate);
    void visitGameBoardAction(InvalidAction invalidAction);
    void visitGameBoardAction(WPCUpdate wpcUpdate);
    void visitGameBoardAction(TokensUpdate tokensUpdate);
    void visitGameBoardAction(DraftPoolRoundTrackUpdate draftPoolUpdate);
    void visitGameBoardAction(ToolCardUse toolCardUse);
}
