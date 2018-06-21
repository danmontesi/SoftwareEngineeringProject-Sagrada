package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public class WPCChoice implements GUIReply {

    private String wpcards;

    public WPCChoice(String wpcards) {
        this.wpcards = wpcards;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public String getWpcards() {
        return wpcards;
    }
}
