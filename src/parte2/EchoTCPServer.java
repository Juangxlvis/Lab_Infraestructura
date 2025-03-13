package parte2;

import java.io.*;
import java.net.*;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(3400);
        System.out.println("The Echo TCP server is running on port 3400 ...");

        while (true) {
            Socket serverSideSocket = listener.accept();
            System.out.println("A client has connected from: " + serverSideSocket.getRemoteSocketAddress());

            // Crear hilo para cada cliente
            new Thread(() -> {
                try {
                    BufferedReader fromNetwork = new BufferedReader(
                            new InputStreamReader(serverSideSocket.getInputStream()));
                    PrintWriter toNetwork = new PrintWriter(serverSideSocket.getOutputStream(), true);

                    String message = fromNetwork.readLine();
                    System.out.println("[Server] From client " + serverSideSocket.getRemoteSocketAddress() + ": " + message);
                    toNetwork.println(message);

                    serverSideSocket.close();
                } catch (IOException e) {
                    System.err.println("Error with client: " + e.getMessage());
                }
            }).start();
        }
    }
}