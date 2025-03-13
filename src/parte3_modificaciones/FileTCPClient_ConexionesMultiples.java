package parte3_modificaciones;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FileTCPClient_ConexionesMultiples {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 3400;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese la ruta del archivo a enviar: ");
            String filePath = scanner.nextLine();
            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {
                System.out.println("El archivo no existe o no es válido.");
                return;
            }

            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    // Para cada línea, abrir una nueva conexión
                    try (Socket clientSideSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                         PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
                         BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()))) {

                        toNetwork.println(line);  // Enviar línea
                        String response = fromNetwork.readLine();  // Recibir respuesta
                        System.out.println("[Servidor]: " + response);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Archivo enviado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
