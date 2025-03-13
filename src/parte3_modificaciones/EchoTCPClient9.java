package parte3_modificaciones;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient9 {
    public static void main(String[] args) {
        try (Socket clientSideSocket = new Socket("localhost", 3400);
             PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
             BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Ingrese su mensaje (escriba 'FIN' en una línea nueva para terminar):");

            while (true) {
                String line = scanner.nextLine();
                toNetwork.println(line); // Enviar línea por línea

                if (line.equalsIgnoreCase("FIN")) { // Indicar fin de mensaje
                    break;
                }
            }

            String fromServer = fromNetwork.readLine();  // Recibir respuesta
            System.out.println("[Client] Del servidor: " + fromServer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}