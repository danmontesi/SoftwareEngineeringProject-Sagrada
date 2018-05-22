package it.polimi.se2018.network.socket;

import it.polimi.se2018.MVC.ClientController;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.*;
import java.net.Socket;

public class ClientConnectionSocket implements ClientConnection { // implements ClientConnecterInterface


    /**
     * This class establish a connection from Client with the server
     */

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientController clientController;

    public ClientConnectionSocket(String host, int port, ClientController controller){
        this.clientController = controller;
        try {
            this.socket = new Socket(host, port);
        }
        catch(IOException e){
                e.printStackTrace();
            }
        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected.");
        } catch (IOException e) {
            System.out.println("Connection Error.");
            e.printStackTrace();
        }
    }


    //per ricevere commands
    public void run () {
        System.out.println("Listening for messages from the Server. ");

        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {
            ServerToClientCommand command;
            while(loop != false){
                try {
                    command = (ServerToClientCommand) input.readObject();
                    System.out.println("clientsocketlistener: ho letto oggetto e ora lo notifico");
                    clientController.applyCommand((command)); // TODO BINDING MAYBE WORKS OR MAYBE DONT
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }


    public void sendCommand(ClientToServerCommand command){
        try {
            output.writeObject(command);
            output.flush();
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
