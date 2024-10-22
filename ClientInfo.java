public class ClientInfo {
    private ClientHandler clientHandler;
    private Thread thread;

    public ClientInfo(ClientHandler clientHandler, Thread thread) {
        this.clientHandler = clientHandler;
        this.thread = thread;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public Thread getThread() {
        return thread;
    }
}
