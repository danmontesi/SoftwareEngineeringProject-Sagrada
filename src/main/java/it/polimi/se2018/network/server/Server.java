package it.polimi.se2018.network.server;


import it.polimi.se2018.CONSTANTS;
import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.rmi.RMIVirtualClient;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.network.server.socket.SocketVirtualClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Server {

    /**
     * Clients that are waiting for a game to start and saved by username
     */
    private static List<String> waitingClients = new ArrayList<>();
    /**
     * Clients connected with their own username and ClientConnection:
     * ClientConnection is the reference that the server has to contact them
     * These clients could both be in a game or be waiting for a game to start
     */
    private static Map<String, ClientConnection> connectedClients = new HashMap<>();

    /**
     * Clients that were in a game andd then got disconnected
     * These clients could be reinserted in a paused game when they reconnect to the server
     * Clients are saved by their unique username
     */
    private static List<String> disconnectedClients = new ArrayList<>();
    /**
     * List of active games (one Thread for each game)
     */
    private static List<Controller> activeGames = new ArrayList<>();
    /**
     * Map used to pass a command coming from the network to the right controller (the right game) to manage it
     */
    private static Map<String, VirtualView> userMap = new HashMap<>();

    private static Timer timer;

    public static void main(String[] args) {

        boolean activeServer = true;
        //pubblica RMI impl server side
        new RMIServer().RMIStartListening();
        //listen socket connections
        new SocketServer().socketStartListening();


        new Thread(() -> {
            while (activeServer) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Problem!");
                    Thread.currentThread().interrupt();
                }
                for (String user : connectedClients.keySet()) {
                    connectedClients.get(user).notifyClient(new PingConnectionTester());  //Checking if still connected(for RMI)
                }
                removeInactiveControllers();
            }
        }
        ).start();

    }

    private static void removeInactiveControllers() {
        for (int i = 0; i < activeGames.size(); i++) {
            if (activeGames.get(i).getOrderedRoundPlayers().isEmpty() && activeGames.get(i).getOrderedPlayers().isEmpty())
                activeGames.remove(i);
        }
    }

    public static void handle(ClientToServerCommand command){
        String username = command.getUsername();
        userMap.get(username).notify(command);
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
        2) Correct login request. view is added to waitingClients (waiting for a game to start)
        3) Attempt to connect with a user already taken, the server choose a similar valid one and notifies it to the view
        Every time, a response is sent back to notify success or failure
         */
        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
            connectedClients.put(username, vc);
            vc.notifyClient(new AuthenticatedCorrectlyCommand(username));
            vc.notifyClient(new MessageFromServerCommand("You reconnected!"));
            refreshBoardAndNotifyReconnectedPlayer(username);
        } else if(!connectedClients.containsKey(username)){
            connectedClients.put(username, vc);
            addToWaitingClients(username);
            vc.notifyClient(new AuthenticatedCorrectlyCommand(username));
        } else {
            Integer i = 1;
            while (true){
                String newUser = username + i.toString();
                if(!connectedClients.containsKey(newUser)){
                    connectedClients.put(newUser, vc);
                    addToWaitingClients(newUser);
                    vc.notifyClient(new AuthenticatedCorrectlyCommand(newUser));
                    return;
                }
                i++;
            }
        }
    }

    private static void notifyNewConnectedPlayer(String username){
        for (String connectionReference : waitingClients){
            if (!connectionReference.equals(username)) {
                getConnectedClients().get(connectionReference).notifyClient(new NewConnectedPlayerNotification(username));
            }
        }

    }

    public static void addClientInterface(Socket socket, ObjectInputStream input, ObjectOutputStream output, String username){

        //Create reference to Socket view

        SocketVirtualClient vc = new SocketVirtualClient(socket, input, output);

        /*
        Three cases:
        1) Attempt to reconnect after disconnection
        2) Correct login request. view is added to waitingClients (waiting for a game to start)
        3) Attempt to connect with a user already taken, the server choose a similar valid one and notifies it to the view
        Every time, a response is sent back to notify success or failure
         */
        System.out.println("Entro in addClientSocket");
        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
            connectedClients.put(username, vc);
            vc.start();
            vc.notifyClient(new MessageFromServerCommand("You reconnected!"));
            refreshBoardAndNotifyReconnectedPlayer(username);
        }  else if (!connectedClients.containsKey(username)){
            connectedClients.put(username, vc);
            System.out.println("prima di inviare AuthCommand");
            vc.notifyClient(new AuthenticatedCorrectlyCommand(username));
            vc.start();
            addToWaitingClients(username);
        } else {
            Integer i = 1;
            while (true){
                String newUser = username + i.toString();
                if(!connectedClients.containsKey(newUser)){
                    connectedClients.put(newUser, vc);
                    vc.notifyClient(new AuthenticatedCorrectlyCommand(newUser));
                    vc.start();
                    addToWaitingClients(newUser);
                    return;
                }
                i++;
            }
        }

    }

    /**
     * Remove any reference of a given player from the server
     * @param username
     */
    public static void removeClient(String username){
        if (connectedClients.containsKey(username)){
            connectedClients.remove(username);
        }
        if (waitingClients.contains(username)){
            waitingClients.remove(username);
        }
        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
        }
    }

    /**
     * Disconnect a player from the server: his username is saved in disconnectedClients in case he will reconnect
     * @param username
     */
    public static synchronized void disconnectClient(String username){
        if (waitingClients.contains(username)) { //Covers the case in which a player is connected but isn't in a started game
            removeClient(username);
            if (waitingClients.size() == 1) {
                timer.cancel();
            }
            return;
        }
        if(connectedClients.containsKey(username)){
            connectedClients.remove(username);
            disconnectedClients.add(username);
            updateDisconnectedUser(username);
            int counter = 0;
            String lastPlayer = null;
            Controller game = getControllerFromUsername(username);
            for (String user : game.getUserViewMap().keySet()){
                if (connectedClients.containsKey(user)){
                    counter++;
                    lastPlayer = user;
                }
            }
            if (counter==1){
                ArrayList<String> fakeScores = new ArrayList<>();
                fakeScores.add("1_" + lastPlayer + "_999");
                game.getUserViewMap().get(lastPlayer).winMessage(fakeScores);
                activeGames.remove(game);
                removeClient(lastPlayer);
            }
        } else {
            System.out.println("Could not found such view");
        }
    }

    public synchronized static void addToWaitingClients(String username){ //TODO Gestire la concorrenza: se vengono addati insieme 6 view, faccio partire un timer e l'altro per i 2 player rimanenti?
        waitingClients.add(username);
        System.out.println("Addato "+ username);
        notifyNewConnectedPlayer(username);
        if (waitingClients.size() == 2){
            System.out.println("Starting timer");
            timer = new Timer(); //TODO gestire il caso di più partite in attesa: devo avere degli ARRAY di timer in quel caso! e sapere quali sono attivi
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            // Inutile -> itsTimeToStart = true;  //TODO a cosa serve questa variabile? startNewGame() deve essere controllato da questa variabile? deve essere messo in un thread quindi?
                            System.out.println("Time expired");
                            //TODO: check all players from RMI are connected -> Ping
                            //wait for 1s (time to the ping to be sent)

                            //if arrives here, the client connected are more than 1 (if not, disconnectPlayers can cancel the timer
                            startNewGame(); //DA TOGLIERE, l'ho utilizzato solo come prova. il metodo deve essere contorllato dalla variabile itsTimeToStart
                        }
                    },
                    CONSTANTS.LOBBY_TIMER //TODO import from file
            );
        }
        if (waitingClients.size()==4){
            startNewGame();
        }
    }

    public static synchronized void startNewGame(){
        //when a game starts, timer is cancelled
        timer.cancel();
        List<String> players = new ArrayList<>();

        for(int i = 0; i < waitingClients.size() || i < 4; i ++){
            try {
                players.add(waitingClients.get(i));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        for(String user : players){
            waitingClients.remove(user);
        }

        Controller controller = new Controller(players);
        activeGames.add(controller);
    }

    public static void refreshBoardAndNotifyReconnectedPlayer(String username){
        for (Controller game : activeGames){
            for (String user : game.getUserViewMap().keySet()){
                if (username.equals(user)){
                    game.getModel().notifyRefreshBoard(username, null);
                }
                else{
                    game.getUserViewMap().get(user).messageBox("Player " + username + " has reconnected");
                }
            }
        }
    }

    public static void updateDisconnectedUser(String username){
        try {
            Controller game = getControllerFromUsername(username);
            for (String usernameToNotify : game.getUserViewMap().keySet()) {
                if (!username.equals(usernameToNotify))
                    game.getUserViewMap().get(usernameToNotify).messageBox("Player" + username + " is disconnected");
            }
        }
        catch (NoSuchElementException e){
            System.out.println("Error: No controller found, error");
        }
    }

    private static Controller getControllerFromUsername(String username){
        for (Controller game : activeGames){
            for (String user : game.getUserViewMap().keySet()){
                if (username.equals(user)){ //found the right controller
                   return game;
                }
            }
        }
        throw new NoSuchElementException();
    }

    public static List<String> getWaitingClients(){
        return waitingClients;
    }

    public static Map<String, ClientConnection> getConnectedClients() {
        return connectedClients;
    }

    public static List<String> getDisconnectedClients() {
        return disconnectedClients;
    }

    public static List<Controller> getActiveGames() {
        return activeGames;
    }

    public static Map<String, VirtualView> getUserMap() {
        return userMap;
    }

}