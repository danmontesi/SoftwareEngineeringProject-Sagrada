package client.view.gui.notifiers.wpcchoiceactions;

/**
 * This is the interface representing all possible commands from GuiView to WPCChoice
 * @author Nives Migotto
 */
public interface WPCChoiceAction {
    void acceptWPCChoiceVisitor(WPCChoiceVisitor wpcChoiceVisitor);
}
