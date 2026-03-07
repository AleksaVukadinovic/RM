package DateTime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DateTimeServer {
    public static final int PORT = 8000;

    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(PORT);
        ) {
            while (true) {
                try (
                        Socket client = server.accept();
                        PrintStream writer = new PrintStream(
                                new BufferedOutputStream(
                                        client.getOutputStream()
                                ), true
                        );

                        Scanner scanner = new Scanner(
                                new BufferedInputStream(
                                        client.getInputStream()
                                )
                        )
                ) {
                    while (true) {
                        String option = scanner.next();
                        System.out.println("[DEBUG] Received command: " + option);
                        LocalDateTime now = LocalDateTime.now();
                        String response;
                        switch (option) {
                            case "DATE":
                                response = now.toLocalDate().toString();
                                break;
                            case "TIME":
                                response = now.toLocalTime().toString();
                                break;
                            case "DATETIME":
                                response = now.toString();
                                break;
                            default:
                                response = "ERROR!";
                                break;
                        }
                        writer.println(response);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
