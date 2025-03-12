package parte4_logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class EchoTCPClienteLogica {
    public static void main(String[] args) throws IOException {
        Socket socketCliente = new Socket("localhost", 3400);
        PrintWriter haciaRed = new PrintWriter(socketCliente.getOutputStream(), true);
        BufferedReader desdeRed = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

        BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Elija una tarea (1: Conversión a hexadecimal, 2: Bits con 0s, 3: Bits con 1s):");
        int tarea = Integer.parseInt(entradaUsuario.readLine());

        if (tarea == 1) {
            System.out.print("Ingrese un número entero para convertir a hexadecimal: ");
            int numero = Integer.parseInt(entradaUsuario.readLine());
            System.out.print("Ingrese la cantidad de dígitos hexadecimales: ");
            int digitos = Integer.parseInt(entradaUsuario.readLine());
            haciaRed.println("HEX:" + numero + "," + digitos);
        } else if (tarea == 2 || tarea == 3) {
            Random aleatorio = new Random();
            StringBuilder cadenaBits = new StringBuilder();
            for (int i = 0; i < 32; i++) {
                cadenaBits.append(aleatorio.nextInt(2));
            }
            System.out.println("Cadena de bits generada: " + cadenaBits);
            System.out.print("Ingrese un número n (2-30): ");
            int n = Integer.parseInt(entradaUsuario.readLine());
            haciaRed.println((tarea == 2 ? "BITS0:" : "BITS1:") + cadenaBits + "," + n);
        }

        String desdeServidor = desdeRed.readLine();
        System.out.println("[Cliente] Desde el servidor: " + desdeServidor);
        socketCliente.close();
    }
}
