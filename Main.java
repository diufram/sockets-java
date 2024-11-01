public class Main {
    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP(8000); // Escoge el puerto que desees
        servidor.iniciar();
    }
}
