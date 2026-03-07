package intro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketIntro {
    public static int PORT = 9000;

    public static void main(String[] args) {
        try (
                // socket(), bind(), listen()
                ServerSocket server = new ServerSocket(PORT);
        ) {
            while (true) {
                Socket client = server.accept();
                Thread worker = new Thread(new ServerWorker(client));
                worker.start();
            }
        } catch (IOException e) {
            System.err.println("Communication error!");
        }
    }
}
