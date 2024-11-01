import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManagerImpl implements ClientManager, ClientEventListener {
    private final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private final List<ClientEventListener> listeners = new ArrayList<>();

    public void addListener(ClientEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClientEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyClientConnected(String clientId) {
        ClientEvent event = new ClientEvent(this, clientId);
        listeners.forEach(listener -> listener.clientConnected(event));
    }

    private void notifyClientDisconnected(String clientId) {
        ClientEvent event = new ClientEvent(this, clientId);
        listeners.forEach(listener -> listener.clientDisconnected(event));
    }

    private void notifyMessageReceived(String clientId, String message) {
        ClientEvent event = new ClientEvent(this, clientId, message);
        listeners.forEach(listener -> listener.messageReceived(event));
    }

    @Override
    public void addClient(ClientHandler client) {
        clients.put(client.getClientId(), client);
        notifyClientConnected(client.getClientId());
    }

    @Override
    public void removeClient(String clientId) {
        clients.remove(clientId);
        notifyClientDisconnected(clientId);
    }

    @Override
    public Optional<ClientHandler> getClient(String clientId) {
        return Optional.ofNullable(clients.get(clientId));
    }

    @Override
    public void broadcastMessage(String message) {
        clients.values().forEach(client -> client.sendMessage(message));
    }

    // Implementación de los métodos de ClientEventListener
    @Override
    public void clientConnected(ClientEvent event) {
        System.out.println("Cliente conectado: " + event.getClientId());
    }

    @Override
    public void clientDisconnected(ClientEvent event) {
        System.out.println("Cliente desconectado: " + event.getClientId());
    }

    @Override
    public void messageReceived(ClientEvent event) {
        System.out.println("Mensaje recibido de " + event.getClientId() + ": " + event.getMessage());
        broadcastMessage(event.getClientId() + ": " + event.getMessage());
    }
}
