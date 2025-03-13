package parte3_modificaciones;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient7 {
    public static void main(String[] args) {
        try (Socket clientSideSocket = new Socket("localhost", 3400);
             PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
             BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Ingrese un mensaje: ");
            String userMessage = scanner.nextLine();  // Leer entrada del usuario

            toNetwork.println(userMessage);  // Enviar al servidor

            String fromServer = fromNetwork.readLine();  // Recibir respuesta
            System.out.println("[Client] Del servidor: " + fromServer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
