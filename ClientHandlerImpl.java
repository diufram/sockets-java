import java.io.*;
import java.net.*;

public class ClientHandlerImpl implements ClientHandler {
    private final Socket clientSocket;
    private final ClientManager clientManager;
    private final ClientEventListener eventListener;
    private final String clientId;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandlerImpl(Socket clientSocket, ClientManager clientManager, ClientEventListener eventListener, String clientId) throws IOException {
        this.clientSocket = clientSocket;
        this.clientManager = clientManager;
        this.eventListener = eventListener;
        this.clientId = clientId;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                eventListener.messageReceived(new ClientEvent(this, clientId, message));
            }
        } catch (IOException e) {
            System.out.println("Error en la conexión de " + clientId + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            clientManager.removeClient(clientId);
            eventListener.clientDisconnected(new ClientEvent(this, clientId));
        } catch (IOException e) {
            System.out.println("Error al cerrar conexión de " + clientId + ": " + e.getMessage());
        }
    }
}
