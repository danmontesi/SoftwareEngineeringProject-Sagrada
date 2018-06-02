package it.polimi.se2018.network.client;


import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.network.server.ServerConnection;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        ServerConnection server;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleziona\n1) Socket\n2) RMI");
        int choice = scanner.nextInt();

        switch (choice){
            case 1:
                server = new SocketClient();
                server.startConnection();
                System.out.println("Connected with Socket");
                break;
            default:
                server = new RMIClient();
                server.startConnection();
                System.out.println("Connected with RMI");
                break;
        }


    }


}
