package parte3_modificaciones;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class EchoTCPServer {
    public static void main(String[] args) {
        final int PORT = 3400;
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("El servidor está corriendo en el puerto " + PORT + "...");

            while (true) {
                System.out.println("Esperando conexión...");
                Socket serverSideSocket = listener.accept();
                System.out.println("Cliente conectado desde " + serverSideSocket.getInetAddress());

                try (BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                     PrintWriter toNetwork = new PrintWriter(serverSideSocket.getOutputStream(), true)) {

                    System.out.println("[Servidor] Recibiendo archivo línea por línea...");

                    String line;
                    while ((line = fromNetwork.readLine()) != null) {
                        if (line.equalsIgnoreCase("FIN")) { // Indica que el cliente terminó de enviar
                            System.out.println("[Servidor] Fin de transmisión detectado.");
                            break;
                        }

                        System.out.println("[Servidor] Recibido: " + line);
                        toNetwork.println("Recibido: " + line); // Enviar confirmación al cliente
                    }

                } catch (SocketTimeoutException e) {
                    System.out.println("Tiempo de espera agotado. Cerrando conexión...");
                }
                System.out.println("[Servidor] Cliente desconectado.");
                serverSideSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
