package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

public class TurnUpdate implements GUIReply {
    private boolean move;
    private boolean tool;

    public TurnUpdate(boolean move, boolean tool) {
        this.move = move;
        this.tool = tool;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public boolean getMove() {
        return move;
    }

    public boolean getTool() {
        return tool;
    }
}
