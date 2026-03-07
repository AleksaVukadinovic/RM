package DateTime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

public class DateTimeClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", DateTimeServer.PORT);

                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()), true);

                Scanner scanner = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()));
        ) {
            while (true) {
                System.out.println("What information do you need? (DATE/TIME/DATETIME)");
                Scanner consoleScanner = new Scanner(System.in);
                String command = consoleScanner.next();
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    System.out.println("Connection closed");
                    break;
                }
                if (!command.equals("DATE") && !command.equals("TIME") && !command.equals("DATETIME")) {
                    System.err.println("Wrong command!");
                    return;
                }

                writer.println(command);
                if (scanner.hasNext()) {
                    System.out.println(scanner.next());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
