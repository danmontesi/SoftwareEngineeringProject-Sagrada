package it.polimi.se2018.network.socket;

import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.client_to_server_command.ChosenToolCardCommand;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.client_to_server_command.UpdateUsernameCommand;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.network.ServerConnection;
import it.polimi.se2018.server_to_client_command.NotifyCredentialsNeeded;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectionSocket extends ServerConnection {

    private ObjectInputStream inSocket;

    private ObjectOutputStream outSocket;

    private Socket socket;

    private Server server;

    //Represents the username of the client

    private boolean operative;

    private Controller controller;

    private ClientConnectionSocket client;

    private Thread thread;

    public ServerConnectionSocket(Socket socket, Server server) throws IOException {
        System.out.println("Entrato nella classe ServerConnectionSocket");
        this.socket = socket;
        this.server = server;

        try {
            inSocket = new ObjectInputStream(this.socket.getInputStream());
            outSocket = new ObjectOutputStream(this.socket.getOutputStream());
            outSocket.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread = new Thread(this);
        thread.start();
        System.out.println("Ho startato");
        sendCommand(new NotifyCredentialsNeeded());

    }

    public void sendCommand(ServerToClientCommand command) throws IOException {
            outSocket.writeUnshared(command);
            outSocket.flush();
            outSocket.reset();

    }


/*
    public void close() {
        if (!closed) {
            try {
                closed = true;
                socket.close();
                inSocket.close();
                outSocket.close();
            } catch (Exception e) {
            }
        }
    }

*/
    /**
     * Reveices commands from Client
     */
    public void run() {
        System.out.println("Sono dentro run"); // non entra
        ClientToServerCommand command;
        operative = true;
        while (operative != false) {
            System.out.println("Receiver di ClientToServerCommand: operativo");
            command = null;
            try {
                command = (ClientToServerCommand) inSocket.readObject();
                System.out.println("arrivato a server un nuovo command");
                if (command instanceof UpdateUsernameCommand) //Case-> is a request of a username (The controller doesn't exist yet)
                    server.receiveCredentialFromConnection(((UpdateUsernameCommand) command).getUsername(), this);
                else
                    command.execute(this, controller); //Execute arriva a chiamare il metodo applyCommand() dal controller, poi sfrutterà il binding dinamico su ApplyCommand
                //server.receiveCredentialFromConnection(command, this);

            } catch (ClassNotFoundException | IOException e) {
                // TODO Close...
                break;
            }
        }
            //if (command instanceof RequestClosureCommand)
           //     closedByClient();

                // commands that can be sent in an asyncronous way from the clients
                // and are always valid
                // and managed by the ServerCommandHandler

           // else if (command instanceof SendCredentialsCommand || command instanceof ChosenLeaderCardCommand
             //       || command instanceof ChatMessageClientCommand || command instanceof ChurchSupportCommand
            //     || command instanceof SatanChoiceCommand) {


    }
/*

    @Override
    public void addObserver(MatchHandlerObserver matchObserver) {
        this.matchObserver = matchObserver;
    }

    @Override
    public void addCommandObserver(ServerCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void addCommandObserver(Server server) {
        this.serverListener=server;
    }

*/

}
