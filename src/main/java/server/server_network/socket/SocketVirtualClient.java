package server.server_network.socket;


import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.network_interfaces.ClientConnection;
import server.Server;
import shared.commands.server_to_client_command.ServerToClientCommand;

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
            String disconnecting;
            for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                if (entry.getValue().equals(this)) {
                    disconnecting = entry.getKey();
                    Server.disconnectClient(disconnecting);
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed()){
            try {
                ClientToServerCommand command = (ClientToServerCommand) input.readObject();
                Server.handle(command);
            } catch (IOException e) {
                try{
                    socket.close();
                } catch (IOException f){
                    //nothing
                }

                String disconnecting;
                for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                    if(entry.getValue().equals(this)){
                        disconnecting = entry.getKey();
                        Server.disconnectClient(disconnecting);
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }

}
