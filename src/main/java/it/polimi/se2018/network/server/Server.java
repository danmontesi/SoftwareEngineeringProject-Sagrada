package it.polimi.se2018.network.server;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.rmi.RMIVirtualClient;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.network.server.socket.SocketVirtualClient;
import it.polimi.se2018.server_to_client_command.AskAuthenticationCommand;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    /**
     * List of clients that waits to play.
     * When the second is added, a timer should start
     * When the timer finishes, or there is a fourth player who is waiting, the server has to create a Thread and starting a
     * new Controller with those players.
     * //TODO method that creates the Controller and set all the HashMaps
     * //TODO Timer for start the game
     */
    private static ArrayList<ClientConnection> waitingClients = new ArrayList<>();

    /**
     * The map contains the Usernames and the relative Connection.
     * Has to be updated every time a player disconnect (removing it, adding to a List of disconnectedPlayers)
     * and add a new Entry String-ClientConnection when the user reconnect with a different connection
     * //TODO gestire la disconnessione
     */
    private static HashMap<String, ClientConnection> usernameConnectionMap = new HashMap<>();

    /**
     * The ArrayList contains the disconnectedClientsUsername that wait to be reassociated to a connection.
     */
    private static ArrayList<String> disconnectedClientsUsername = new ArrayList<>();

    /**
     * Ordered Active Games
     */
    private static ArrayList<Controller> activeGames = new ArrayList<>();

    /**
     * The map let the Server to obtain the correct controller given a Username, that is useful in order to apply a Command from Client
     */
    private static HashMap<String, Controller> userMap = new HashMap<>();

    /**
     * The map let the Controller know which are the connections of their connected clients.
     * The map has to remain in the server, that is static, because only the server knows when a Username is Disconnected
     * //TODO This map has to be updated every time a player disconnects! Replacing / removing the relative username Connection.
     */
    private static HashMap<Controller, ArrayList<ClientConnection> > controllerClientConnectionMap = new HashMap<>();

    public static void main(String[] args) {

        Server server = new Server();

        //pubblica RMI impl server side
        new RMIServer().RMIStartListening();
        System.out.println("Listening RMI");
        //listen socket connections
        new SocketServer().socketStartListening();
        System.out.println("Listening Socket");
    }

    public static void handle(ClientToServerCommand command){
        //TODO: Se il comando è un login si gestisce aggiungendo l'user ai connectedClients, altrimenti...
        String[] words = command.getMessage().split(" ");
        if (words[0].equals("UpdateUsernameCommand")){
            System.out.println("ARRIVATO COMMAND " + command.getMessage()); //stringa fatta da 3 parole: 0) nomeClass 1) oldUsernameSetByServer 2) newUsernameFromClient
            //TODO controllo se l'username va bene (ovvero words[2]), se no NE ASSEGNO UNO
            //Se sì, faccio
            usernameConnectionMap.put(words[2], usernameConnectionMap.remove(words[1]));
            System.out.println(usernameConnectionMap.toString());
        }
        else {
            String username = command.getUsername();
            //Obtains the right controller from the map, and calls the method on it
            userMap.get(username).distinguishClientCommand(username, command);
        }
    }


    /**
     * New connections are automatically added to waitingClients because they haven't chosen a username yet.
     * They are later moved from waitingClients to connnectedClients when they are connected with their user.
     * @param client
     */
    public synchronized static void addClientInterface(RMIClientInterface client){
        RMIVirtualClient vc = new RMIVirtualClient(client);
        waitingClients.add(waitingClients.size(), vc);
        String tempConnectionId = usernameConnectionMap.size()+"";
        //Insert the connection in the hashmap with the tempId
        usernameConnectionMap.put(tempConnectionId, vc);
        // Viene fatta partire la richiesta di username subito dopo la connessione, che prende direttament l'username dal parametro deciso al momento della connessione
        vc.notifyClient(new AskAuthenticationCommand(usernameConnectionMap.size() + "") ); //l'id è la dimensione del
    }

    //TODO fai lo stesso fatto con l'RMI per il socket
    public static void addClientInterface(Socket socket){
        SocketVirtualClient vc = new SocketVirtualClient(socket);
        waitingClients.add(waitingClients.size(), vc);
        vc.start();
        vc.notifyClient(new AskAuthenticationCommand("id")); //TODO HAS TO BE A RANDOM STRING
    }

    public static ArrayList<ClientConnection> getWaitingClients(){
        return waitingClients;
    }

    public static void removeClient(ClientConnection client){
        waitingClients.remove(client);
    }

    public static HashMap<String, ClientConnection> getUsernameConnectionMap() {
        return usernameConnectionMap;
    }

    public static ArrayList<String> getDisconnectedClientsUsername() {
        return disconnectedClientsUsername;
    }

    public static ArrayList<Controller> getActiveGames() {
        return activeGames;
    }

    public static HashMap<String, Controller> getUserMap() {
        return userMap;
    }

    public static HashMap<Controller, ArrayList<ClientConnection>> getControllerClientConnectionMap() {
        return controllerClientConnectionMap;
    }
}

