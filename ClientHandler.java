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

    public ClientHandler(Socket socket, String id) {
        this.clientSocket = socket;
        this.clientId = id;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientId + ": " + inputLine);
                // Puedes añadir aquí lógica para redirigir mensajes.
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
