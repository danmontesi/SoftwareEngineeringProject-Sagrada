package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class ContinueTurnCommand extends ServerToClientCommand {

    private boolean move;

    private boolean tool;

    /**
     * Command that let the client continue its turn
     * @param move true if can do a move
     * @param tool true if can use a tool\
     */
    public ContinueTurnCommand(boolean move, boolean tool) {
        this.move = move;
        this.tool = tool;
    }

    public boolean canShowMove() {
        return !move;
    }

    public boolean canShowTool() {
        return !tool;
    }
    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
