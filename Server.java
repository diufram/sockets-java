import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ClientManager clientManager = new ClientManager();
    private MessageDispatcher messageDispatcher;

    public Server(int port) {
        this.port = port;
        messageDispatcher = new MessageDispatcher(clientManager.getClients());
    }


    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor Corriendo..." + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientId = "Client" + clientManager.getClients().size();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId);

                // Creamos el hilo y almacenamos ambos en la clase ClientInfo
                Thread thread = new Thread(clientHandler);
                ClientInfo clientInfo = new ClientInfo(clientHandler, thread);
                clientManager.addClient(clientId, clientInfo);

                // Iniciamos el hilo
                thread.start();

                System.out.println("New client connected: " + clientId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar mensajes a todos
    public void broadcastMessage(String message) {
        messageDispatcher.broadcastMessage(message);
    }

    // Método para enviar un mensaje a un cliente específico
    public void sendMessageToClient(String clientId, String message) {
        messageDispatcher.sendMessageToClient(clientId, message);
    }

    // Método para detener un hilo de cliente
    public void stopClientThread(String clientId) {
        clientManager.stopClientThread(clientId);
    }
}
