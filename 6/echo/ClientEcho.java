package echo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientEcho {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", ServerEcho.PORT);
                PrintStream writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true);
                Scanner socketReader = new Scanner(socket.getInputStream());
                Scanner consoleReader = new Scanner(System.in)
        ) {
            while (consoleReader.hasNextLine()) {
                String line = consoleReader.nextLine();
                if (line.equalsIgnoreCase("exit")) break;

                writer.println(line);
                System.out.println(socketReader.nextLine());
            }
        } catch (IOException e) {
            System.err.println("ClientEcho::Communication error!");
        }
    }
}
