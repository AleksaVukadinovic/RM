package dec_ps_2025_zad2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChessClient {
    public static final String HOST = "localhost";
    public static final int PORT = ChessServer.PORT;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PORT);

            PrintStream writer = new PrintStream(
                new BufferedOutputStream(
                    socket.getOutputStream()
                ), true
            );

            Scanner networkScanner = new Scanner(
                new BufferedInputStream(
                    socket.getInputStream()
                )
            );
        ) {
            Scanner localScanner = new Scanner(System.in);

            while (localScanner.hasNextLine()) {
                String line = localScanner.nextLine();
                if (line.toLowerCase().contains("bye")) {
                    break;
                }
                writer.println(line);
                if (networkScanner.hasNextLine())
                    System.out.println(networkScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
