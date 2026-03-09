package zadatak2_chess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChessClient {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);

             PrintStream writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true);

             Scanner networkScanner = new Scanner(new BufferedInputStream(socket.getInputStream()))

        ) {
            Scanner scanner = new Scanner(System.in);
            String line;
            while (true) {
                line = scanner.nextLine();
                writer.println(line);
                System.out.println(networkScanner.nextLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
