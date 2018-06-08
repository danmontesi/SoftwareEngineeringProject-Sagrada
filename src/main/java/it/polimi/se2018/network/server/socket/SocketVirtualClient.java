package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SocketVirtualClient extends Thread implements ClientConnection {

    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;
    BufferedReader is;

    public SocketVirtualClient(Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
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
        while(!socket.isClosed()){
            try {
                ClientToServerCommand command = (ClientToServerCommand) input.readObject();
                Server.handle(command);
            } catch (IOException e) {
                //if the connection has not been stopped compliantly but consequently to
                //a thrown exception, it is necessary to close the connection with socket.close()
                //in order to avoid an infinite loop
                try{
                    socket.close();
                } catch (IOException f){
                    //niente
                }
                 /*
            Nota: in alternativa, un modo più semplice di ciclare in tutta la mapppa è aggiungere al comando
            da server a client l'username dell'utente di destinazione (come per l'analogo client to server)
             */
                String disconnecting;
                for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                    if(entry.getValue().equals(this)){
                        disconnecting = entry.getKey();
                        Server.disconnnectClient(disconnecting);
                        System.out.println("Client " + entry.getKey() + " disconnected");
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }

}
