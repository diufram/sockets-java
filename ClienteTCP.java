import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {

    private static final String HOST = "localhost";  // Cambia "localhost" por la IP del servidor si no está en tu máquina
    private static final int PUERTO = 8000;         // Debe coincidir con el puerto del servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);

            String mensaje;
            while (true) {
                System.out.print("Escribe un mensaje: ");
                mensaje = teclado.readLine();  // Leer mensaje desde el teclado

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Desconectando...");
                    break;  // Salir del bucle para cerrar la conexión
                }

                salida.println(mensaje);  // Enviar mensaje al servidor
                String respuesta = entrada.readLine();  // Leer respuesta del servidor
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
