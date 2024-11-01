import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    private final int puerto;
    private final ClientManagerImpl clientManager;

    public ServidorTCP(int puerto) {
        this.puerto = puerto;
        this.clientManager = new ClientManagerImpl();
        clientManager.addListener(clientManager); // Registrar clientManager como listener de sus propios eventos
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientId = "Cliente-" + clientSocket.getPort();
                System.out.println(clientId + " conectado.");

                ClientHandler clientHandler = new ClientHandlerImpl(clientSocket, clientManager, clientManager, clientId);
                clientManager.addClient(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
