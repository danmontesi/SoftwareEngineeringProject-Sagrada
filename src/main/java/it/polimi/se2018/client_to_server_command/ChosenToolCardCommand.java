package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.network.ServerConnection;

public class ChosenToolCardCommand {
    int numberChosen;

    public ChosenToolCardCommand(int numberChosen){
        this.numberChosen = numberChosen;
    }


    public void execute(ServerConnection connection, Controller controller){
        controller.applyClientCommand(connection, this);
    }


}
