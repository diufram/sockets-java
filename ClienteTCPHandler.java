import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCPHandler implements Runnable {
    private Socket clientSocket;
    private ServidorTCP servidor;
    private BufferedReader input;
    private PrintWriter output;
    private boolean conectado = true; // Indicador de conexión

    public ClienteTCPHandler(Socket clientSocket, ServidorTCP servidor) {
        this.clientSocket = clientSocket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress());

            String clientMessage;
            while (conectado && (clientMessage = input.readLine()) != null) {
                System.out.println("Mensaje recibido: " + clientMessage);
                String response = MessageService.procesar(clientMessage);
                
                // Enviar el mensaje procesado a todos los clientes conectados, excepto al remitente
                servidor.enviarMensajeATodos(response, this);
            }
        } catch (IOException e) {
            System.out.println("Error de conexión con el cliente: " + e.getMessage());
        } finally {
            cerrarConexion(); // Aseguramos el cierre de la conexión al terminar el bucle
        }
    }

    public void enviarMensaje(String mensaje) {
        if (output != null) {
            output.println(mensaje); // Envía el mensaje al cliente conectado
        }
    }

    public void cerrarConexion() {
        conectado = false; // Esto permitirá que el bucle en `run` se detenga
        servidor.removerCliente(this); // Elimina este cliente de la lista de clientes activos
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Cliente desconectado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
