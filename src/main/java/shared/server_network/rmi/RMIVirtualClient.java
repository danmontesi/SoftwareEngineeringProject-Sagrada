package shared.server_network.rmi;


import shared.client_network.ClientConnection;
import shared.client_network.rmi.RMIClientInterface;
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
