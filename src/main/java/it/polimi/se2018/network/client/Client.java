package it.polimi.se2018.network.client;


import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.view.gui.LoginMain;

import java.util.Scanner;

public class Client{
    /* Default choices: GUI and Socket
       In case of incorrect input no exception is thrown: default choices are applied.*/
    public static void main(String[] args) {
        ServerConnection server;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select \n1) CLI\n2) GUI");
        String viewType = scanner.nextLine();
        if (viewType.equals("1")) {
            System.out.println("Select \n1) Socket\n2) RMI");
            String connectionType = scanner.nextLine();
            System.out.println("Insert IP Address: (press enter to connect to localhost)");
            String ipAddress = scanner.nextLine();
            if(ipAddress.equals("")){
                ipAddress = "127.0.0.1";
            }
            System.out.println("Type the username you want to connect with:");
            String username = scanner.nextLine();

            if(connectionType.equals("2")){
                server = new RMIClient(1, ipAddress);
            } else {

                server = new SocketClient(1, ipAddress);
            }
            server.startConnection(username);
        } else {
            LoginMain.main(args);
        }
    }
}