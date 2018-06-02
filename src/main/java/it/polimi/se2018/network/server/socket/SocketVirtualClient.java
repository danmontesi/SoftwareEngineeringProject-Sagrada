package it.polimi.se2018.network.server.socket;

import client.ClientConnection;
import commands.ClientToServerCommand;
import commands.ServerToClientCommand;
import server.Controller;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VirtualClient extends Thread  implements ClientConnection {

    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;


    public VirtualClient(Socket socket) {
        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyClient(ServerToClientCommand command) {
        try {
            output.writeObject(command);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //or maybe !socket.isclosed()
        while(!socket.isClosed()){
            try {
                ClientToServerCommand command = (ClientToServerCommand) input.readObject();
                Controller.stampa(command);
                //TODO:gestione comando
            } catch (IOException e) {
                //if the connection has not been stopped compliantly but consequently to
                //a thrown exception, it is necessary to close the connection with socket.close()
                //in order to avoid an infinite loop
                try{
                    socket.close();
                } catch (IOException f){
                    //niente
                }
                Server.removeClient(this);
                System.out.println("Client disconnesso");

            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }
}
