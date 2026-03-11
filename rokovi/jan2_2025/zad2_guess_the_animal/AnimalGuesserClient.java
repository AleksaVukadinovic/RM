package zad2_guess_the_animal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class AnimalGuesserClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", AnimalGuesserServer.PORT);

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

                Scanner localScanner = new Scanner(System.in);
        ) {
            System.out.println(networkScanner.nextLine());
            System.out.println(networkScanner.nextLine());
            System.out.println(networkScanner.nextLine());

            String name = localScanner.nextLine();
            writer.println(name);

            while (true) {
                if (networkScanner.hasNextLine()) {
                    String serverMessage = networkScanner.nextLine();
                    System.out.println(serverMessage);

                    if (serverMessage.contains("Correct")) break;
                }

                String myGuess = localScanner.next();
                writer.println(myGuess);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
