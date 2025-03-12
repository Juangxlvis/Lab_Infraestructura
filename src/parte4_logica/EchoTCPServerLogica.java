package parte4_logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServerLogica {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(3400);
        System.out.println("El servidor TCP de eco está funcionando en el puerto 3400 ...");

        while (true) {
            Socket socketServidor = servidor.accept();
            BufferedReader desdeRed = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
            PrintWriter haciaRed = new PrintWriter(socketServidor.getOutputStream(), true);

            String mensaje = desdeRed.readLine();
            System.out.println("[Servidor] Recibido: " + mensaje);

            if (mensaje.startsWith("HEX:")) {
                String[] partes = mensaje.substring(4).split(",");
                int numero = Integer.parseInt(partes[0]);
                int digitos = Integer.parseInt(partes[1]);
                String resultadoHex = String.format("%0" + digitos + "X", numero);
                haciaRed.println(resultadoHex);
            } else if (mensaje.startsWith("BITS0:") || mensaje.startsWith("BITS1:")) {
                String[] partes = mensaje.substring(6).split(",");
                String cadenaBits = partes[0];
                int n = Integer.parseInt(partes[1]);
                char relleno = mensaje.startsWith("BITS0:") ? '0' : '1';
                String resultado = cadenaBits.substring(0, n) + String.valueOf(relleno).repeat(32 - n);
                haciaRed.println(resultado);
            }

            socketServidor.close();
        }
    }
}
