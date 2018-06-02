package it.polimi.se2018.network.server;


import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.rmi.RMIVirtualClient;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.network.server.socket.SocketVirtualClient;

import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<ClientConnection> clients = new ArrayList<>();

    public static void main(String[] args) {

        //pubblica RMI impl server side
        new RMIServer().RMIStartListening();
        System.out.println("Listening RMI");
        //listen socket connections
        new SocketServer().socketStartListening();
        System.out.println("Listening Socket");
    }

    public static void addClientInterface(RMIClientInterface client){
        RMIVirtualClient vc = new RMIVirtualClient(client);
        clients.add(vc);
    }

    public static void addClientInterface(Socket socket){
        SocketVirtualClient vc = new SocketVirtualClient(socket);
        clients.add(vc);
        vc.start();
    }

    public static ArrayList<ClientConnection> getClients(){
        return clients;
    }

    public static void removeClient(ClientConnection client){
        clients.remove(client);
    }
}
