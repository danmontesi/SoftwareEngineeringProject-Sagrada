package it.polimi.se2018.network.server;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.rmi.RMIVirtualClient;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.network.server.socket.SocketVirtualClient;
import it.polimi.se2018.server_to_client_command.AskAuthenticationCommand;
import it.polimi.se2018.server_to_client_command.AuthenticatedCorrectlyCommand;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    /**
     * Clients that are waiting for a game to start and saved by username
     */
    private static ArrayList<String> waitingClients = new ArrayList<>();
    /**
     * Clients connected with their own username and ClientConnection:
     * ClientConnection is the reference that the server has to contact them
     * These clients could both be in a game or be waiting for a game to start
     */
    private static HashMap<String, ClientConnection> connectedClients = new HashMap<>();
    /**
     * Clients that were in a game andd then got disconnected
     * These clients could be reinserted in a paused game when they reconnect to the server
     * Clients are saved by their unique username
     */
    private static ArrayList<String> disconnectedClients = new ArrayList<>();
    /**
     * List of active games (one Thread for each game)
     */
    private static ArrayList<Controller> activeGames = new ArrayList<>();
    /**
     * Map used to pass a command coming from the network to the right controller (the right game) to manage it
     */
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
    }


    /**
     * New connections are automatically added to waitingClients because they haven't chosen a username yet.
     * They are later moved from waitingClients to connnectedClients when they are connected with their user.
     * @param client
     */
    public static void addClientInterface(RMIClientInterface client, String username){

        //Create reference to RMIClient
        RMIVirtualClient vc = new RMIVirtualClient(client);

        /*
        Three cases:
        1) Attempt to reconnect after disconnection
        2) Correct login request. Client is added to waitingClients (waiting for a game to start)
        3) Attempt to connect with a user already taken, the server choose a similar valid one and notifies it to the client
        Every time, a response is sent back to notify success or failure
         */
        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
            connectedClients.put(username, vc);
            vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + username));
        } else if(!connectedClients.containsKey(username)){
            connectedClients.put(username, vc);
            waitingClients.add(username);
            vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + username));
        } else {
            int i = 1;
            while (true){
                String newUser = username + Character.toChars(i);
                if(!connectedClients.containsKey(newUser)){
                    connectedClients.put(newUser, vc);
                    waitingClients.add(newUser);
                    vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + newUser));
                    return;
                }
            }
        }

    }

    public static void addClientInterface(Socket socket, String username){

        //Create reference to Socket Client
        SocketVirtualClient vc = new SocketVirtualClient(socket);
        /*
        Three cases:
        1) Attempt to reconnect after disconnection
        2) Correct login request. Client is added to waitingClients (waiting for a game to start)
        3) Attempt to connect with a user already taken, the server choose a similar valid one and notifies it to the client
        Every time, a response is sent back to notify success or failure
         */
        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
            connectedClients.put(username, vc);
            vc.start();
            vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + username));
        }  else if (!connectedClients.containsKey(username)){
            connectedClients.put(username, vc);
            waitingClients.add(username);
            vc.start();
            vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + username));
        } else {
            int i = 1;
            while (true){
                String newUser = username + Character.toChars(i);
                if(!connectedClients.containsKey(newUser)){
                    connectedClients.put(newUser, vc);
                    waitingClients.add(newUser);
                    vc.start();
                    vc.notifyClient(new AuthenticatedCorrectlyCommand("AuthenticatedCorrectlyCommand " + newUser));
                    return;
                }
            }
        }

    }

    public static ArrayList<String> getWaitingClients(){
        return waitingClients;
    }

    public static void removeClient(String username){
        //sposto il client da connectedClients a disconnnectedClients
    }

    public static void disconnnectClient(String username){
        if(connectedClients.containsKey(username)){
            connectedClients.remove(username);
            disconnectedClients.add(username);
            System.out.println("Client " + username + " disconnected");
        } else {
            System.out.println("Could not found such client");
        }
    }
}
