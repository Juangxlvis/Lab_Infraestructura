package parte1_excepciones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class EchoTCPClient {
    public static void main(String[] args) throws IOException {
        Socket clientSideSocket = new Socket("localhost", 9999);


        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);


        BufferedReader fromNetwork = new BufferedReader(new
                InputStreamReader(clientSideSocket.getInputStream()));


        toNetwork.println("Hello World");
        String fromServer = fromNetwork.readLine();


        System.out.println("[Client] From server: " + fromServer);


        clientSideSocket.close();
    }
}
