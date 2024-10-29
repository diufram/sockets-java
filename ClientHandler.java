import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String clientId;
    private PrintWriter out;
    private BufferedReader in;
    private MessageDispatcher messageDispatcher;
    private volatile boolean running = true;  // Bandera para detener el hilo

    public ClientHandler(Socket socket, String id, MessageDispatcher messageDispatcher) {
        this.clientSocket = socket;
        this.clientId = id;
        this.messageDispatcher = messageDispatcher;

        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    // Método para detener el hilo y cerrar el socket
    public void stop() {
        running = false;  // Cambia la bandera para detener el bucle
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();  // Cierra el socket para detener readLine()
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Client connected: " + clientId);

        try {
            String inputLine;
            while (running && (inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientId + ": " + inputLine);
                messageDispatcher.broadcastMessage(clientId, "From " + clientId + ": " + inputLine);
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace(); // Imprime la excepción solo si el hilo no fue detenido intencionalmente
            }
        } finally {
            closeConnection();  // Asegúrate de cerrar el socket y los recursos
        }
    }

    private void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            System.out.println("Client " + clientId + " disconnected.");
            messageDispatcher.removeClient(clientId); // Notifica a MessageDispatcher sobre la desconexión
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
