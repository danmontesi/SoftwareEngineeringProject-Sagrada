package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketVirtualClient extends Thread implements ClientConnection {

    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

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
            //TODO disconnetti client
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
            da server a view l'username dell'utente di destinazione (come per l'analogo view to server)
             */
                String disconnecting;
                for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                    if(entry.getValue().equals(this)){
                        disconnecting = entry.getKey();
                        Server.disconnectClient(disconnecting);
                        System.out.println("client " + entry.getKey() + " disconnected");
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }

}
