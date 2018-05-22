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

public class ServerConnectionSocket implements ServerConnection {

    private ObjectInputStream inSocket;

    private ObjectOutputStream outSocket;

    private Socket socket;

    private Server server;

    //Represents the username of the client
    private String username;

    private boolean operative;

    private Controller controller;

    private ClientConnectionSocket client;

    public ServerConnectionSocket(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        try {
            inSocket = new ObjectInputStream(this.socket.getInputStream());
            outSocket = new ObjectOutputStream(this.socket.getOutputStream());
            outSocket.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        ClientToServerCommand command;
        operative = true;
        while (operative != false) {
            command = null;
            try {
                command = (ClientToServerCommand) inSocket.readObject();
                controller.applyClientCommand(command);
                System.out.println(" Arrivato a ServerConnection ");
                //TODO c0ntonua

//				System.out.println(command.getClass().getName());

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
