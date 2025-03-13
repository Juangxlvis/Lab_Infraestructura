package parte1_excepciones;

import java.io.*;
import java.net.*;

public class ClaseParte2 {
    public static void main(String[] args) throws IOException {
        // Cambia la IP por la del servidor
        Socket clientSideSocket = new Socket("192.168.206.193", 3400);

        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
        BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));

        toNetwork.println("Hello from another client!");
        String fromServer = fromNetwork.readLine();

        System.out.println("[Client] From server: " + fromServer);

        clientSideSocket.close();
    }
}
