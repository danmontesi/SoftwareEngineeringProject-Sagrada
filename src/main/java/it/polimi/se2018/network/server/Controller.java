package server;

import commands.ClientToServerCommand;
import commands.LoginRequest;

public class Controller {


    //attualmente non funziona
    public static void stampa(LoginRequest command){
        System.out.println("Login Request: " +command.getUsername());
    }

    public static void stampa(ClientToServerCommand command){
        System.out.println("Comando generico: " + command.toString());
    }
}
