package NumberGuesser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGuesser {
    public static final int PORT = 12321;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started on PORT: " + PORT);
            System.out.println("Server is active and listening...");

            Socket client;
            while (true) {
                client = server.accept();
                System.out.println("New player");
                new Thread(new ServerThread(client)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
