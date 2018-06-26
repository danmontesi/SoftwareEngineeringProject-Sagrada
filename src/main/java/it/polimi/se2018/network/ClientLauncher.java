package it.polimi.se2018.network;

import java.util.Scanner;

//TODO: To do better, is an example of connection from a view
public class ClientLauncher {
/*
    public static void main(String args[]){
        ClientConnection connection;
        View view;
        ClientNetworkHandler clientNetworkHandler;

        Scanner i = new Scanner(System.in);

        System.out.println("Choose the view interface you desire: \n1 - Command Line Interface\n2 - Graphic User Interface");

        //Default: cli
        int chosen = i.nextInt();

        if (chosen == 2)
            view = new GUIView();
        else
            view = new CLIView();

        clientNetworkHandler = new ClientNetworkHandler(view);

        System.out.println("Select which is the connection you prefere: \n1 - Socket\n2 - RMI ");

        //Default == Socket
        chosen  = i.nextInt();

        if (chosen == 2)
            connection = new ClientConnectionSocket("localhost", 1111, clientNetworkHandler);
            //connection = new ClientConnectionRMI(controller);
        else{
            connection = new ClientConnectionSocket("localhost", 1111, clientNetworkHandler);
            System.out.println("andrebbe sostituito da RMI");
        }

        //clientNetworkHandler.(connection);
        view.addController(clientNetworkHandler);

        try {
            connection.startThread();
        } catch (Exception e) {
            System.err.println("YOU can't connect");
            e.printStackTrace();
        }

    }

*/
}
