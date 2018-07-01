package it.polimi.se2018.view.gui.notifiers.wpcchoiceactions;

public interface WPCChoiceVisitor {
    void visitWPCChoiceAction(WGUIViewSetting guiViewSetting);
    void visitWPCChoiceAction(WPCChoice wpcChoice);
}
