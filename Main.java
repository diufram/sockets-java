public class Main {
    public static void main(String[] args) {
        int port = 8000;
        Server server = new Server(port);

        // Inicia el servidor en un hilo separado
        Thread serverThread = new Thread(server);
        serverThread.start();

        System.out.println("Servidor iniciado en un hilo aparte.");
    }
}

