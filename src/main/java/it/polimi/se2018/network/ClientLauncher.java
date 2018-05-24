package it.polimi.se2018.network;

import it.polimi.se2018.MVC.CLIView;
import it.polimi.se2018.MVC.ClientNetworkHandler;
import it.polimi.se2018.MVC.GUIView;
import it.polimi.se2018.MVC.View;
import it.polimi.se2018.network.rmi.ClientConnectionRMI;
import it.polimi.se2018.network.socket.ClientConnectionSocket;

import java.util.Scanner;

public class ClientLauncher {

    public static void main(String args[]){
        ClientConnection connection;
        View view;
        ClientNetworkHandler clientNetworkHandler;

        Scanner i = new Scanner(System.in);

        System.out.println("Choose the view interface you desire: \n1 - Command Line Interface\n2 - Graphic User Interface");

        //Default: CLI
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
        else
            connection = new ClientConnectionSocket("localhost", 1111, clientNetworkHandler);

        clientNetworkHandler.addConnection(connection);
        view.addController(clientNetworkHandler);

        try {
            connection.startThread();
        } catch (Exception e) {
            System.err.println("YOU can't connect");
            e.printStackTrace();
        }

    }
}
