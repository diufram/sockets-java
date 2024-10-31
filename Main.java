import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = 8000;
        int maxClients = 3;

        try {
            ServidorTCP servidorTCP = new ServidorTCP(port, maxClients);
            Thread serverThread = new Thread(servidorTCP);
            serverThread.start(); // Inicia el servidor en un hilo independiente

            // Para detener el servidor cuando sea necesario, puedes llamar a:
            // servidorTCP.detener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
