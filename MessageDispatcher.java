public class MessageDispatcher {

    private ClientManager clientManager;

    public MessageDispatcher(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

 /*    public void broadcastMessage(String message) {
        for (ClientInfo clientInfo : clientManager.getClients().values()) {
            System.out.println("Sending to " + clientInfo.getClientHandler().getClientId());
            clientInfo.getClientHandler().sendMessage(message);
        }
    } */
        // Enviar un mensaje a todos los clientes menos al remitente
        public void broadcastMessage(String senderId, String message) {
            for (ClientInfo clientInfo : clientManager.getClients().values()) {
                // Omite el cliente que envió el mensaje
                if (!clientInfo.getClientHandler().getClientId().equals(senderId)) {
                    clientInfo.getClientHandler().sendMessage(message);
                }
            }
        }
    // Enviar un mensaje a un cliente específico
    public void sendMessageToClient(String clientId, String message) {
        ClientInfo clientInfo = clientManager.getClientInfo(clientId);
        if (clientInfo != null) {
            clientInfo.getClientHandler().sendMessage(message);
        } else {
            System.out.println("Client not found: " + clientId);
        }
    }

    // Opcional: Método para eliminar un cliente (si quieres gestionarlo desde aquí)
    public void removeClient(String clientId) {
        clientManager.removeClient(clientId);
    }
}
