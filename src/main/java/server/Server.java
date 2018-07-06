package server;

import shared.CONSTANTS;
import server.server_network.rmi.RMIServer;
import server.server_network.socket.SocketServer;
import server.server_network.socket.SocketVirtualClient;
import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.*;
import shared.network_interfaces.ClientConnection;
import shared.network_interfaces.RMIClientInterface;
import server.server_network.rmi.RMIVirtualClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    /**Clients that are waiting for a game to start and saved by username */
    private static List<String> waitingClients = new ArrayList<>();

    /**
     * Clients connected with their own username and ClientConnection:
     * ClientConnection is the reference that the server has to contact them
     * These clients could both be in a game or be waiting for a game to start
     */
    private static Map<String, ClientConnection> connectedClients = new ConcurrentHashMap<>();

    /** Clients that were in a game andd then got disconnected
     *  These clients could be reinserted in a paused game when they reconnect to the serverClients are saved by their unique username
     */
    private static List<String> disconnectedClients = new ArrayList<>();

    /** List of active games (one Thread for each game) */
    private static List<Controller> activeGames = new ArrayList<>();

    /** Map used to pass a command coming from the network to the right controller (the right game) to manage it */
    private static Map<String, VirtualView> userMap = new HashMap<>();

    /** Timer before starting a match after 2 connected players */
    private static Timer timer;

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());


    public static void main(String[] args) {
        boolean activeServer = true;

        new RMIServer().RMIStartListening();
        new SocketServer().socketStartListening();

        new Thread(() -> {
            while (activeServer) {
                try {
                    Thread.sleep(CONSTANTS.PING_TIMER);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                try {
                    for (String user : connectedClients.keySet()) {
                        LOGGER.log(Level.FINE, "Now sending ping to->" + user);
                        connectedClients.get(user).notifyClient(new PingConnectionTester());  //Checking if still connected(for RMI)
                    }
                    removeInactiveControllers();
                }
                catch (NullPointerException e){
                    //nothing, user deleted before
                }
            }
        }
        ).start();

    }

    /**
     * Removes inactive controllers
     */
    private static void removeInactiveControllers() {
        for (int i = 0; i < activeGames.size(); i++) {
            if (!activeGames.get(i).isActive()) {
                for (String user : activeGames.get(i).getUserViewMap().keySet()){
                    removeClient(user);
                }
                activeGames.remove(i);
            }
        }
    }

    /**
     * Method called by VirtualView sending the message from the Controller through the network to the real Client View
     * @param command command that has to be sent
     */
    public static void handle(ClientToServerCommand command){
        String username = command.getUsername();
        userMap.get(username).notify(command);
    }


    /**
     * Adds clients (RMI)
     * New connections are automatically added to waitingClients because they haven't chosen a username yet.
     * They are later moved from waitingClients to connnectedClients when they are connected with their user.
     */
    public static void addClientInterface(RMIClientInterface client, String username){
        //Create reference to RMIClient
        RMIVirtualClient vc = new RMIVirtualClient(client);

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

    /**
     * Notifies other clients of a new player connection
     */
    private static void notifyNewConnectedPlayer(String username){
        for (String connectionReference : waitingClients){
            if (!connectionReference.equals(username)) {
                getConnectedClients().get(connectionReference).notifyClient(new NewConnectedPlayerNotification(username));
            }
        }

    }

    /**
     * Adds clients (Socket)
     */
    public static void addClientInterface(Socket socket, ObjectInputStream input, ObjectOutputStream output, String username){
        //Create reference to SocketClient
        SocketVirtualClient vc = new SocketVirtualClient(socket, input, output);

        if (disconnectedClients.contains(username)){
            disconnectedClients.remove(username);
            connectedClients.put(username, vc);
            vc.start();
            vc.notifyClient(new AuthenticatedCorrectlyCommand(username));
            vc.notifyClient(new MessageFromServerCommand("You reconnected!"));
            refreshBoardAndNotifyReconnectedPlayer(username);
        }  else if (!connectedClients.containsKey(username)){
            connectedClients.put(username, vc);
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
     * Removes all references of a given player from the server
     */
    private static void removeClient(String username){
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
     * Disconnects a player from the server: his username is saved in disconnectedClients in case he reconnects
     */
    public static synchronized void disconnectClient(String username){
        if (waitingClients.contains(username)) { //Covers the case in which a player is connected but isn't in a started game
            removeClient(username);
            if (waitingClients.size() == 1) {
                timer.cancel();
                System.out.println("There are less than 2 players in the WaitingList, Timer cancelled");
            }
            return;
        }
        if(connectedClients.containsKey(username)){
            connectedClients.remove(username);
            disconnectedClients.add(username);
            updateDisconnectedUser(username, false);
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
                game.getUserViewMap().get(lastPlayer).endGame();
                ArrayList<String> fakeScores = new ArrayList<>();
                fakeScores.add("1_" + lastPlayer + "_999");
                game.getUserViewMap().get(lastPlayer).winMessage(fakeScores);
                game.setInactive();
                removeClient(lastPlayer);
            }
        } else {
           //username has already been removed before
        }
    }

    /**
     * Adds players to a game and starts the game
     */
    private static synchronized void addToWaitingClients(String username){
        waitingClients.add(username);
        System.out.println("Connected new player, whose name is "+ username);
        notifyNewConnectedPlayer(username);
        if (waitingClients.size() == 2){
            System.out.println("Starting timer");
            timer = new Timer();
            timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Timer expired! \nStarting a new match!");
                        startNewGame();
                    }
                },
                CONSTANTS.LOBBY_TIMER
            );
        }
        if (waitingClients.size()==4){
            startNewGame();
        }
    }

    /**
     * Starts a new game
     */
    private static synchronized void startNewGame(){
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
        waitingClients.removeAll(players);

        Controller controller = new Controller(players, false);
        activeGames.add(controller);
    }

    /**
     * Sends a game board refresh to reconnected players
     */
    private static void refreshBoardAndNotifyReconnectedPlayer(String username){
        for (Controller game : activeGames){
            for (String user : game.getUserViewMap().keySet()){
                if (username.equals(user)){
                    game.getModel().notifyBoardSinglePlayer(userMap.get(username));
                    game.getModel().notifyRefreshDraftPool();
                    game.getModel().setGamePlayers(game.getOrderedPlayers());
                    if (game.getModel().getCurrentPlayer().getUsername().equals(username)){
                        game.getUserViewMap().get(username).startTurnMenu();
                    }
                    else{
                        game.getUserViewMap().get(username).otherPlayerTurn(game.getModel().getCurrentPlayer().getUsername());
                    }
                }
                else{
                    System.out.println("Player " + username + "has reconnected :)");
                    game.getUserViewMap().get(user).messageBox("Player " + username + " has reconnected :)");
                }
            }
        }
    }

    /**
     * Notifies other players of the disconnection of a player
     */
    static void updateDisconnectedUser(String username, boolean isTurnStartNotification){
        try {
            Controller game = getControllerFromUsername(username);
            for (String usernameToNotify : game.getUserViewMap().keySet()) {
                if (!username.equals(usernameToNotify)) {
                    if (isTurnStartNotification){
                        game.getUserViewMap().get(usernameToNotify).messageBox(username + " is disconnected so skips its turn");
                    }
                    else {
                        System.out.println("Player " + username + "has disconnected :(");
                        game.getUserViewMap().get(usernameToNotify).messageBox("Player " + username + " has disconnected :(");
                    }
                }
            }
        }
        catch (NoSuchElementException e){
            LOGGER.log(Level.FINE,"Controller just deleted");
        }
    }

    /**
     * Retrieves the game in which a client is playing from its username
     */
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

    public static Map<String, ClientConnection> getConnectedClients() {
        return connectedClients;
    }

    static Map<String, VirtualView> getUserMap() {
        return userMap;
    }

}