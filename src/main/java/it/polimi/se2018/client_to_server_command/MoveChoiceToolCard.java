package it.polimi.se2018.client_to_server_command;

public class MoveChoiceToolCard  extends ClientToServerCommand{
    public int getNumberChosen() {
        return numberChosen;
    }

    /**
     * The number of chosen Tool is relative to the ordered ToolCards in the Controller
     * The message is just className
     */
    private int numberChosen;

    public MoveChoiceToolCard(String message, int numberChosen){
        this.numberChosen = numberChosen;
        this.message = "MoveChoiceToolCardCommand";
    }

}
