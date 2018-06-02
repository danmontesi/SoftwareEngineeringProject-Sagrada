package it.polimi.se2018.network.server;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;

/*
    SO CHE IL CONTROLLER NON DOVREBBE ESSERE QUA!
    QUESTO ERA SOLO UN CONTROLLER DI PROVA PER VEDERE SE
    LE COSE FUNZIONAVANO, E VA TUTTO BENE.
 */


public class Controller {

    public static void stampa(ClientToServerCommand command){
        System.out.println("Comando generico: " + command.toString());
    }
}
