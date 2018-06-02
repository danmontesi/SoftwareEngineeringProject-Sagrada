package client;

import client.rmi.RMIClient;
import client.socket.SocketClient;
import commands.LoginRequest;
import server.ServerConnection;

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

        String username = scanner.nextLine();
        server.send(new LoginRequest(username));



    }


}
