package it.polimi.se2018.server_to_client_command;

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
}
