public interface ClientHandler extends Runnable {
    void sendMessage(String message);
    String getClientId();
    void closeConnection();
}
