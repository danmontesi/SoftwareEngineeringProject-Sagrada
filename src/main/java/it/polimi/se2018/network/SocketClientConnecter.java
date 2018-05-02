package it.polimi.se2018.network;

public class SocketClientConnecter { // implements ClientConnecterInterface


    /**
     * This class establish a connection from Client with the server
     */

    // TODO CALLED: CLientSocketInterface -> Invia comandi al serve
/*

    public void connect() {
        try {
            socket = new Socket(NetworkConstants.SERVER_IP_ADDRESS, NetworkConstants.PORT);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try{
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            outSocket.flush();
            inSocket = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // create listener
        listener = new ClientSocketListener(inSocket);

        hear = new Thread(listener);
        hear.start();
    }

    */
}
