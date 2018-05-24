package it.polimi.se2018.network.socket;

import it.polimi.se2018.MVC.ClientNetworkHandler;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.*;
import java.net.Socket;
import java.util.Observable;

public class ClientConnectionSocket extends ClientConnection {


    /**
     * This class establish a connection from Client with the server
     */

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientNetworkHandler clientController;
    private Thread thread;

    public ClientConnectionSocket(String host, int port, ClientNetworkHandler clientNetworkHandler){
        this.clientController = clientNetworkHandler;
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


    public void startThread(){
        thread = new Thread(this);
        thread.start();
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
            System.out.println("Inviato da connectionSocket");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(ClientToServerCommand command) { //Chiamato dal ClientConnection, fa l'update su lcontroller -> invia un evento di risposta
        sendCommand(command);
    }
}
