package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;

public class RefreshBoard implements GUIReply {

    private RefreshBoardCommand modelRepresentation;

    public RefreshBoard(RefreshBoardCommand modelRepresentation) {
        this.modelRepresentation = modelRepresentation;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public RefreshBoardCommand getModelRepresentation() {
        return modelRepresentation;
    }
}
