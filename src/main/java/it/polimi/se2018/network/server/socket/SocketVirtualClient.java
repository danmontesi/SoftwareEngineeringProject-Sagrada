package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketVirtualClient extends Thread  implements ClientConnection {

    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    public SocketVirtualClient(Socket socket) {
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
                Server.removeClient(this);
                System.out.println("Client disconnesso");

            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }
}
