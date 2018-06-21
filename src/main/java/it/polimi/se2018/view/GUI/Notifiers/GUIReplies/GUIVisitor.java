package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public interface GUIVisitor {
    void visitGUIReply(RefreshBoard refreshBoard);
    void visitGUIReply(GUIViewSetting guiViewSetting);
    void visitGUIReply(WPCChoice wpcChoice);
    void visitGUIReply(TurnStart turnStart);
    void visitGUIReply(TurnUpdate turnUpdate);
    void visitGUIReply(InvalidAction invalidAction);
    void visitGUIReply(WPCUpdate wpcUpdate);
    void visitGUIReply(TokensUpdate tokensUpdate);
    void visitGUIReply(RoundTrackUpdate roundTrackUpdate);
    void visitGUIReply(DraftPoolUpdate draftPoolUpdate);
}
