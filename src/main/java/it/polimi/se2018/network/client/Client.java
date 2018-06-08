package it.polimi.se2018.network.client;


import it.polimi.se2018.MVC.CLIView;
import it.polimi.se2018.MVC.View;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.network.server.ServerConnection;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        ServerConnection server;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select \n1) Socket\n2) RMI");
        int choice = scanner.nextInt();
        System.out.println("Type the username you want to connect with:");
        String username = scanner.next();

        switch (choice){
            case 1:
                server = new SocketClient(1);
                server.startConnection(username);
                break;
            case 2:
                server = new RMIClient(1);
                server.startConnection(username);
                break;
        }
    }


}