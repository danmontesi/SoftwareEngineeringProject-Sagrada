package it.polimi.se2018.network.socket;

public class SocketListener {

/** Is a SocketListener of connections.
 * Server has one for each kind of network (RMI and Socket)
 */
}



/*
    @Override
    public void run() {
        try {
            startListening();
        } catch (IOException e) {

            try {
                endListening();
            } catch (IOException e2) {
            }
        }
    }

     * this method create a server socket and accept all connection.
     *
      @throws IOException Signals that an I/O exception has occurred.

    private void startListening() throws IOException {

        server = new ServerSocket(port);
        listening = true;
        int i = 0;// number of clients accepted
        while (listening) {
            Socket socket = server.accept();
            ClientHandler c = new ClientHandlerSocket(socket, i, creator);
            creator.addClient(c);
            i++;//
        }

    }
*/