package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.network.client.rmi.RMIClientInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;

import java.rmi.RemoteException;
import java.util.Map;

public class RMIVirtualClient implements ClientConnection {

    RMIClientInterface rmiClientInterface;

    public RMIVirtualClient(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void notifyClient(ServerToClientCommand command) {

            new Thread() {
                public void run() {
                    try {
                        rmiClientInterface.rmiNotifyClient(command);
                    } catch (RemoteException e) {
            /*
            Nota: in alternativa, un modo più semplice di ciclare in tutta la mapppa è aggiungere al comando
            da server a view l'username dell'utente di destinazione (come per l'analogo view to server)
             */
                        String disconnecting;
                        for (Map.Entry<String, ClientConnection> entry : Server.getConnectedClients().entrySet()) {
                            if (entry.getValue().equals(this)) {  //TODO: con il thread non mi prende this come connessione! COme risolvo?
                                disconnecting = entry.getKey();
                                Server.disconnectClient(disconnecting);
                                System.out.println("client " + entry.getKey() + " disconnected");
                                break;
                            }
                        }
                    }
                }
            }.start();
            //rmiClientInterface.rmiNotifyClient(command);


    }
}

// bella