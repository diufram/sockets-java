import java.util.Map;
import java.util.HashMap;

public class ClientManager {

    private Map<String, ClientInfo> clients = new HashMap<>();

    public void addClient(String clientId, ClientInfo clientInfo) {
        clients.put(clientId, clientInfo);
    }

    public void removeClient(String clientId) {
        clients.remove(clientId);
    }

    public ClientInfo getClientInfo(String clientId) {
        return clients.get(clientId);
    }

    public void stopClientThread(String clientId) {
        ClientInfo clientInfo = clients.get(clientId);
        if (clientInfo != null) {
            clientInfo.getThread().interrupt(); // Detenemos el hilo interrumpiéndolo
            removeClient(clientId);
            System.out.println("Client " + clientId + " disconnected.");
        } else {
            System.out.println("Client thread not found: " + clientId);
        }
    }

    public Map<String, ClientInfo> getClients() {
        return clients;
    }
}
