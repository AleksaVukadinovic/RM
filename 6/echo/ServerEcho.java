package echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {
    public static int PORT = 9000;

    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(PORT)
        ) {
            while (true) {
                Socket client = server.accept();
                Thread worker = new Thread(new ServerWorker(client));
                worker.start();
            }
        } catch (IOException e) {
            System.err.println("ServerEcho::Communication error!");
        }
    }
}
