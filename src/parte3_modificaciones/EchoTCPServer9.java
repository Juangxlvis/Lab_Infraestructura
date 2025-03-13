package parte3_modificaciones;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class EchoTCPServer9 {
    public static void main(String[] args) {
        final int PORT = 3400;
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("El servidor está corriendo en el puerto " + PORT + "...");

            while (true) {
                System.out.println("Esperando conexión...");
                Socket serverSideSocket = listener.accept();
                System.out.println("Cliente conectado desde " + serverSideSocket.getInetAddress());

                serverSideSocket.setSoTimeout(10000); // 10 segundos de espera

                try (BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                     PrintWriter toNetwork = new PrintWriter(serverSideSocket.getOutputStream(), true)) {

                    System.out.println("[Server] Esperando mensajes...");
                    StringBuilder receivedMessage = new StringBuilder();
                    String message;

                    while ((message = fromNetwork.readLine()) != null) {
                        if (message.equalsIgnoreCase("FIN")) { // Cliente indica el final del mensaje
                            break;
                        }
                        receivedMessage.append(message).append("\n");
                    }

                    System.out.println("[Server] Recibido:\n" + receivedMessage);
                    toNetwork.println("Mensaje recibido correctamente"); // Respuesta al cliente

                } catch (SocketTimeoutException e) {
                    System.out.println("Tiempo de espera agotado. Cerrando conexión...");
                }
                serverSideSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}