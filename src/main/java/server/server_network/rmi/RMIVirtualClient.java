package server.server_network.rmi;


import client.client_network.ClientConnection;
import client.client_network.rmi.RMIClientInterface;
import server.Server;
import shared.commands.server_to_client_command.ServerToClientCommand;

import java.rmi.RemoteException;
import java.util.Map;

public class RMIVirtualClient implements ClientConnection{

    RMIClientInterface rmiClientInterface;

    public RMIVirtualClient(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void notifyClient(ServerToClientCommand command) {

        try {
            rmiClientInterface.rmiNotifyClient(command);
        } catch (RemoteException e) {
            /*
            Nota: in alternativa, un modo più semplice di ciclare in tutta la mapppa è aggiungere al comando
            da server a view l'username dell'utente di destinazione (come per l'analogo view to server)
             */
            String disconnecting;
            for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                if (entry.getValue().equals(this)) {
                    disconnecting = entry.getKey();
                    Server.disconnectClient(disconnecting);
                    System.out.println("client " + entry.getKey() + " disconnected");
                    break;
                }
            }
        }

    }
}
