package it.polimi.se2018.network.client.rmi;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.utils.ControllerClientInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient extends Thread implements Remote, ServerConnection{

    private Registry registry;
    private RMIServerInterface server;

    public ControllerClientInterface getClientController() {
        return clientController;
    }

    public static Deque<ServerToClientCommand> getCommandQueue() {
        return commandQueue;
    }

    private ClientController clientController;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private ServerToClientCommand command;
    private boolean dispatched = false;
    private final static Object waiter = new Object();

    private static Deque<ServerToClientCommand> commandQueue;

    public RMIClient(int viewChoice){
        clientController = new ClientController(viewChoice, this);
        commandQueue = new ConcurrentLinkedDeque<>();
        Thread commandHandlerRMI = new Thread(new CommandHandlerRMI(this));
        commandHandlerRMI.start();
    }

    @Override
    public void send(ClientToServerCommand command) {
        try {
            if (!command.hasUsername()){
                LOGGER.log(Level.INFO, "Connection not open yet: please start connection first");
                return;
            }
            server.rmiSend(command);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void notifyRMI(ServerToClientCommand command) {
        /*
        new Thread(() -> {
            clientController.dispatchCommand(command);
        }).start();
        */
        commandQueue.add(command);
        System.out.println("added "+command);

    }
        /*
        synchronized (waiter) {
            if (command.toString().contains("Ping")) {
                LOGGER.log(Level.FINE, "RMI: arriva comando", command);
                return;
            }
            commandQueue.add(command);
            waiter.notifyAll();
        }
        System.out.println("Uscito da synchronized Sta finendo!" + command.toString());*/

    @Override
    public void run() {
        while (true) {
            synchronized (commandQueue) {
                if (!commandQueue.isEmpty()) {
                    System.out.println("Sono fuori");
                    System.out.println("Sono vivo");
                    clientController.dispatchCommand(commandQueue.poll());
                }
            }
        }
        }
        /*
        System.out.println(this);
        while (true) {
            System.out.println("0: " + commandQueue.toString());
            synchronized (waiter) {
                System.out.println("1: " + commandQueue.toString());
                while (commandQueue.isEmpty()) {
                    try {
                        System.out.println("prima di wait");
                        waiter.wait();
                        System.out.println("non waita più");
                        System.out.println("2: " + commandQueue.toString());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("prima di dispatch");
                    ServerToClientCommand command = commandQueue.remove();
                    clientController.dispatchCommand(command);
                }
                System.out.println("3: " + commandQueue.toString());
            }
        }*/

    @Override
    public void startConnection(String username) {
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (RMIServerInterface)registry.lookup("RMIImplementation");
            RMIClientImplementation client = new RMIClientImplementation(this);
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            server.addClient(remoteRef, username);
        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}