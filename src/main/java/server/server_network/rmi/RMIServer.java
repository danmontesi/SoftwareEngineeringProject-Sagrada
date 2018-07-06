package server.server_network.rmi;

import shared.CONSTANTS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Manages RMI connection (server side)
 * @author Alessio Molinari
 */
public class RMIServer {
    private static int port = CONSTANTS.RMI_PORT;

    /**
     * Starts listening
     */
    public void RMIStartListening(){
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            RMIServerImplementation serverImplementation = new RMIServerImplementation();
            registry.rebind("RMIImplementation", serverImplementation);
            System.out.println("Listening RMI, address: " + InetAddress.getLocalHost() + " port: " + port);
        } catch (RemoteException e) {
            //nothing
        } catch (UnknownHostException e) {
            //nothing
        }
    }
}
