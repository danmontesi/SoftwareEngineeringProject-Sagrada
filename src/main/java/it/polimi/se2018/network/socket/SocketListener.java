package it.polimi.se2018.network.socket;

import it.polimi.se2018.network.Server;
import it.polimi.se2018.network.ServerConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener {

/** Is a SocketListener of connections.
 * Server has one for each kind of network (RMI and Socket)
 */

    /**
     * The port.
     */
    private int port;

    /**
     * The listening.
     */
    private boolean operative;

    /**
     * The creator.
     */
    private Server server;

    private ServerSocket serverSocket;

    /**
     * Instantiates a new server socket listener.
     *
     * @param server the server starter
     */
    public SocketListener(Server server, int port) {
        this.port = 1111; // TODO change in costant
        operative = true;
        this.server = server;
        System.out.println("Faccio run");
        run();

    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            startListening();
            System.out.println("Faccio shtart list");
        } catch (IOException e) {
            System.out.println("end");

        }
    }

    /**
     * this method create a server socket and accept all connection.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void startListening() throws IOException {

        System.out.println("Starto");
        serverSocket = new ServerSocket(port);
        operative = true;
        while (operative) {
            System.out.println("Sono dentro il while");
            Socket socket = serverSocket.accept();
            ServerConnection c = new ServerConnectionSocket(socket, server);
            server.addConnection(c);
        }

    }

    /**
     * this method closed all waiting ClientHandlerSocket and the server socket.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void endListening() throws IOException {
        if (operative) {
            operative = false;
        }
    }
}

