import java.util.Map;

public class MessageDispatcher {

    private Map<String, ClientInfo> clients;

    public MessageDispatcher(Map<String, ClientInfo> clients) {
        this.clients = clients;
    }

    // Enviar un mensaje a todos los clientes
    public void broadcastMessage(String message) {
        for (ClientInfo clientInfo : clients.values()) {
            clientInfo.getClientHandler().sendMessage(message);
        }
    }

    // Enviar un mensaje a un cliente específico
    public void sendMessageToClient(String clientId, String message) {
        ClientInfo clientInfo = clients.get(clientId);
        if (clientInfo != null) {
            clientInfo.getClientHandler().sendMessage(message);
        } else {
            System.out.println("Client not found: " + clientId);
        }
    }
}
