package it.polimi.se2018.client_to_server_command;

public class MoveChoiceToolCard  extends ClientToServerCommand{
    int numberChosen;

    private String message;

    public MoveChoiceToolCard(String message, int numberChosen){
        this.numberChosen = numberChosen;
        this.message = "MoveChoiceToolCardCommand";
    }

}
