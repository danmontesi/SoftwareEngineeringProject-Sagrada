package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public class DraftPoolUpdate implements GUIReply {
    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }
}
