package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class ContinueTurnCommand extends ServerToClientCommand {
    private boolean move;
    private boolean tool;

    /**
     * Allows the user to continue his turn
     * @param move true if can do a move, false otherwise
     * @param tool true if can use a tool\
     */
    public ContinueTurnCommand(boolean move, boolean tool) {
        this.move = move;
        this.tool = tool;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public boolean hasPerformedMove() {
        return move;
    }

    public boolean hasPerformedTool() {
        return tool;
    }
}
