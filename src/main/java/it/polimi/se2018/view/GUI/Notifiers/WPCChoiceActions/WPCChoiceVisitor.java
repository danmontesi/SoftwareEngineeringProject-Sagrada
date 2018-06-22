package it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions;

public interface WPCChoiceVisitor {
    void visitWPCChoiceAction(WGUIViewSetting guiViewSetting);
    void visitWPCChoiceAction(WPCChoice wpcChoice);
}
