import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClienteTCP {
    private static final String HOST = "localhost";  // Cambia "localhost" por la IP del servidor si no está en tu máquina
    private static final int PUERTO = 8000;         // Debe coincidir con el puerto del servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             PrintWriter salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);

            // Hilo para recibir mensajes del servidor
            Thread recibirMensajes = new Thread(() -> {
                try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String respuesta;
                    while ((respuesta = entrada.readLine()) != null) {
                        System.out.println("Mensaje del servidor: " + respuesta);
                    }
                } catch (SocketException e) {
                    // Esto se espera cuando el socket se cierra; ignoramos esta excepción
                    System.out.println("Conexión cerrada.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            recibirMensajes.start();  // Inicia el hilo de recepción de mensajes

            // Bucle para enviar mensajes al servidor
            String mensaje;
            while (true) {
                mensaje = teclado.readLine();  // Leer mensaje desde el teclado

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Desconectando...");
                    break;  // Salir del bucle para cerrar la conexión
                }

                salida.println(mensaje);  // Enviar mensaje al servidor
            }

            // Cierra el socket después de salir del bucle
            socket.close();
            System.out.println("Socket cerrado, cliente desconectado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
