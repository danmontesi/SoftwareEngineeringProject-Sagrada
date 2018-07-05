package client.view.gui.notifiers.wpcchoiceactions;

public interface WPCChoiceVisitor {
    void visitWPCChoiceAction(WGUIViewSetting guiViewSetting);
    void visitWPCChoiceAction(WPCChoice wpcChoice);
}
