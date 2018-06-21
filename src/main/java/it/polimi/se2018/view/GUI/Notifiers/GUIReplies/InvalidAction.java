package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public class InvalidAction implements GUIReply {
    private String message;

    public InvalidAction(String message) {
        this.message = message;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public String getMessage() {
        return message;
    }
}
