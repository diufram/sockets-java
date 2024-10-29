import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable { // Implementamos Runnable
    private int port;
    private ClientManager clientManager = new ClientManager();
    private MessageDispatcher messageDispatcher;

    public Server(int port) {
        this.port = port;
        messageDispatcher = new MessageDispatcher(clientManager);
    }

    @Override
    public void run() {
        startServer(); // Ejecutamos el servidor dentro del método run()
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor corriendo en el puerto: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientId = "Client" + clientManager.getClients().size();

                // Crear ClientHandler y pasar MessageDispatcher
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, messageDispatcher);

                // Crear e iniciar el hilo
                Thread thread = new Thread(clientHandler);
                clientManager.addClient(clientId, new ClientInfo(clientHandler, thread));

                thread.start();
                System.out.println("Nuevo cliente conectado: " + clientId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar mensajes a todos
    public void broadcastMessage(String message,String clientId) {
        messageDispatcher.broadcastMessage(clientId,message);
    }

    // Método para enviar un mensaje a un cliente específico
    public void sendMessageToClient(String clientId, String message) {
        messageDispatcher.sendMessageToClient(clientId, message);
    }

      // Método para detener el hilo de un cliente específico
      public void stopClientThread(String clientId) {
        ClientInfo clientInfo = clientManager.getClientInfo(clientId);
        if (clientInfo != null) {
            clientInfo.stopHandler();  // Detener el hilo usando el método stopHandler en ClientInfo
            clientManager.removeClient(clientId);  // Remover el cliente del manager
            System.out.println("Cliente " + clientId + " ha sido desconectado.");
        } else {
            System.out.println("Cliente no encontrado: " + clientId);
        }
    }
}
