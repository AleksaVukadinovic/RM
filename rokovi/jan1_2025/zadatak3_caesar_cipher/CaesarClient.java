package zadatak3_caesar_cipher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CaesarClient {
    private static final int PORT = 5555;
    private static final String HOST = "localhost";

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
            while (true) {
                System.out.print("Enter command: ");
                String prompt = localScanner.nextLine();
                if (prompt.equals("EXIT")) {
                    break;
                }
                writer.println(prompt);
                System.out.println(networkScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
