package NumberGuesser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class ClientGuesser {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", ServerGuesser.PORT);

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

                Scanner localScanner =  new Scanner(System.in);
        ) {
            while (networkScanner.hasNextLine()) {
                String message = networkScanner.nextLine();
                System.out.println(message);
                if (message.contains("Pogodili")) break;

                int guess = localScanner.nextInt();
                writer.println(guess);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
