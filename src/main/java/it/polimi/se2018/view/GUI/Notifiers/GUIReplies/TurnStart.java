package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public class TurnStart implements GUIReply {

    private String username;

    public TurnStart(String username) {
        this.username = username;
    }
    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public String getUsername() {
        return username;
    }
}
