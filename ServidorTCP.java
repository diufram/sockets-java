import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTCP implements Runnable {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private List<ClienteTCPHandler> clientesConectados; // Lista para almacenar clientes
    private boolean running = true;

    public ServidorTCP(int port, int maxClients) throws IOException {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(maxClients);
        clientesConectados = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("Servidor TCP iniciado en el puerto " + serverSocket.getLocalPort());
        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                ClienteTCPHandler clienteHandler = new ClienteTCPHandler(clientSocket, this); // Pasamos el servidor al handler
                clientesConectados.add(clienteHandler); // Agregamos el cliente a la lista
                executorService.submit(clienteHandler);
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        } finally {
            detener();
        }
    }

    public void enviarMensajeATodos(String mensaje, ClienteTCPHandler remitente) {
        for (ClienteTCPHandler cliente : clientesConectados) {
            if (cliente != remitente) { // Evita enviar el mensaje de vuelta al remitente
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    public void removerCliente(ClienteTCPHandler cliente) {
        clientesConectados.remove(cliente);
    }

    public void detener() {
        running = false;
        try {
            serverSocket.close();
            executorService.shutdown();
            System.out.println("Servidor TCP detenido.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
