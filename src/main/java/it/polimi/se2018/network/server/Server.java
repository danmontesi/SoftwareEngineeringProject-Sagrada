package it.polimi.se2018.network.server;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.rmi.RMIVirtualClient;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.network.server.socket.SocketVirtualClient;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private static ArrayList<ClientConnection> waitingClients = new ArrayList<>();
    private static HashMap<String, ClientConnection> connectedClients = new HashMap<>();
    private static ArrayList<String> disconnectedClients = new ArrayList<>();
    private static ArrayList<Controller> activeGames = new ArrayList<>();
    private static HashMap<String, Controller> userMap = new HashMap<>();

    public static void main(String[] args) {

        //pubblica RMI impl server side
        new RMIServer().RMIStartListening();
        System.out.println("Listening RMI");
        //listen socket connections
        new SocketServer().socketStartListening();
        System.out.println("Listening Socket");
    }

    public static void handle(ClientToServerCommand command){
        //TODO: Se il comando è un login si gestisce aggiungendo l'user ai connectedClients, altrimenti...
        String username = command.getUsername();
        userMap.get(username).update(username, command);
        //TODO: conflitto String/ClientConnection come parametro di update
        //Ti prego rinomina il metodo update <3
    }


    /**
     * New connections are automatically added to waitingClients because they haven't chosen a username yet.
     * They are later moved from waitingClients to connnectedClients when they are connected with their user.
     * @param client
     */
    public static void addClientInterface(RMIClientInterface client){
        RMIVirtualClient vc = new RMIVirtualClient(client);
        waitingClients.add(vc);
    }

    public static void addClientInterface(Socket socket){
        SocketVirtualClient vc = new SocketVirtualClient(socket);
        waitingClients.add(vc);
        vc.start();
    }

    public static ArrayList<ClientConnection> getWaitingClients(){
        return waitingClients;
    }

    public static void removeClient(ClientConnection client){
        waitingClients.remove(client);
    }
}
