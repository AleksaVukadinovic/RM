package zadatak3_caesar_cipher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CaesarServerWorker implements Runnable {
    private final Socket client;

    CaesarServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serve();
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void serve() {
        try (
                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                client.getOutputStream()
                        ), true
                );

                Scanner scanner = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                );
        ) {
            label:
            while (true) {
                String[] commandAndArguments = scanner.nextLine().split(" ");
                if (commandAndArguments.length == 1 && commandAndArguments[0].equals("EXIT")) {
                    break;
                }
                if (commandAndArguments.length < 3) {
                    writer.println("Invalid command format.");
                    break;
                }
                String command = commandAndArguments[0];
                int shiftSize = Integer.parseInt(commandAndArguments[1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < commandAndArguments.length; i++) {
                    sb.append(commandAndArguments[i]).append(" ");
                }
                String text = sb.toString();
                switch (command) {
                    case "EXIT":
                        writer.println("Connection closed");
                        break label;
                    case "ENC":
                        writer.println("Result: " + encrypt(text, shiftSize));
                        break;
                    case "DEC":
                        writer.println("Result: " + decrypt(text, shiftSize));
                        break;
                    default:
                        writer.println("Invalid command format.");
                        break label;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String encrypt(String input, int shiftSize) {
        StringBuilder sb = new StringBuilder();
        int shift = (shiftSize % 26 + 26) % 26;

        for (char c : input.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                char shifted = (char) (((c - 'a' + shift) % 26) + 'a');
                sb.append(shifted);
            } else {
                sb.append(c);
            }
        }
        return sb.toString().trim();
    }

    private String decrypt(String input, int shiftSize) {
        return encrypt(input, -shiftSize);
    }
}
