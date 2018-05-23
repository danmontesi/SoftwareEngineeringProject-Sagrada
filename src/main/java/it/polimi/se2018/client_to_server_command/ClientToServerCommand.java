package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.MVC.Controller;

import java.io.Serializable;

public class ClientToServerCommand implements Serializable {

    private static final long serialVersionUID = -6460847931998831472L;
    /**
     * Represent all possible methods from Client to Server
     * They are constructed by the View
     *
     */


    public void execute(Controller controller){
        controller.applyClientCommand(this);
    }

}


//... Many others

class ChosenUsernameCommand{

}