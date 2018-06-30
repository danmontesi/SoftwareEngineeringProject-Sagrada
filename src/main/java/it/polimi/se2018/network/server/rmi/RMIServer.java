package it.polimi.se2018.network.server.rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    private static int port = 1099;



    public void RMIStartListening(){


        try {
            Registry registry = LocateRegistry.createRegistry(port);
            RMIServerImplementation serverImplementation = new RMIServerImplementation();
            registry.rebind("RMIImplementation", serverImplementation);
            System.out.println("Listening RMI, address: " + InetAddress.getLocalHost() + " port: " + port);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
