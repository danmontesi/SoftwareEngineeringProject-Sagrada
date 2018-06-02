package server;

import client.ClientConnection;
import client.rmi.RMIClientInterface;
import server.rmi.RMIServer;
import server.rmi.RMIVirtualClient;
import server.socket.SocketServer;
import server.socket.VirtualClient;

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
        VirtualClient vc = new VirtualClient(socket);
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
