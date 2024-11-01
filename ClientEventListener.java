public interface ClientEventListener {
    void clientConnected(ClientEvent event);
    void clientDisconnected(ClientEvent event);
    void messageReceived(ClientEvent event);
}
