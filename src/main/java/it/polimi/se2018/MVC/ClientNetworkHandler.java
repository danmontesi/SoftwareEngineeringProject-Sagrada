package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.client_to_server_command.*;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.server_to_client_command.*;

public class ClientNetworkHandler {

    /**
     * La classe che viene in contatto con la connessione (Socket o RMI)
     *
     * Ha un duplice scopo:
     * Da una parte, presenta tutti i metodi che chiamano apply ( ServerToClientCommand ...)
     * per binding dinamico, verranno acceduti i metodi corretti.
     *
     * Dall'altra, si occupa di inviare i comandi del tipo ClientToServerCommand alla connessione
     *
     *
     */

    private View view;

    private Model playerModel;

    private ClientConnection connection;

    public ClientNetworkHandler(View view){
        this.view = view;
    }
    // Main method for sending commands to Server

    public void sendCommand(ClientToServerCommand command) {
        try {
            System.out.println("entro nel send ClientController"); //
             connection.sendCommand(command);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /**
     * Second role of the ClientController is to receive commands from Server and apply them (to Client)
     *
     * I have multiple methods called applyCommand with a different command as parameter
     * Firstly from calling distinguishCommand(), ClientController understand which kind of event server sent and applies it through calling
     * correct applyCommand() methods
     * @param
     */

    public void distinguishServerCommand(ServerToClientCommand command){
        String words[] = command.getMessage().split(" ");
        //TODO complete
        switch(words[0]){
            case("Passa Turn"):
                ;
                break;
            case("ok"):
                //applyClientCommand.....//TODO: cambio da serverConnection a Player ogni metodo applyCommand
                ;
                break;
            default:
                ;//InvalidInput
                break;
        }


    }

    public void applyCommand(ServerToClientCommand command){
        System.out.println("Funziona?");
        if (command instanceof NotifyCredentialsNeeded)
            System.out.println("Dinamicamente ok");
        view.showAskUsernamePanel();

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     
    public void applyCommand(command){

    }
*/


}
