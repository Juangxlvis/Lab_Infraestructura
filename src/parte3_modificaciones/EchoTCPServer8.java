package parte3_modificaciones;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class EchoTCPServer8 {
    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(3400)) {
            System.out.println("El servidor está corriendo en el puerto 3400...");

            while (true) {
                System.out.println("Esperando conexión...");
                Socket serverSideSocket = listener.accept();
                System.out.println("Cliente conectado desde " + serverSideSocket.getInetAddress());

                serverSideSocket.setSoTimeout(10000); // 10 segundos de espera

                try (BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                     PrintWriter toNetwork = new PrintWriter(serverSideSocket.getOutputStream(), true)) {

                    String message = fromNetwork.readLine();
                    if (message == null) {
                        System.out.println("Cliente desconectado.");
                        continue;
                    }

                    System.out.println("[Server] Recibido: " + message);
                    toNetwork.println(message);  // Responder con el mismo mensaje

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
