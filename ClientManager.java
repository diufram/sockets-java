import java.util.Optional;

public interface ClientManager {
    void addClient(ClientHandler client);
    void removeClient(String clientId);
    Optional<ClientHandler> getClient(String clientId);
    void broadcastMessage(String message);
}
