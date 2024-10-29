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

    // Método para detener el hilo de forma segura
    public void stopHandler() {
        clientHandler.stop();  // Detener el bucle de lectura en ClientHandler
        thread.interrupt();     // Interrumpir el hilo en caso de bloqueo
    }
}
