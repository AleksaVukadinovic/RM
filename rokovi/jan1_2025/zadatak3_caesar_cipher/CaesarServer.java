package zadatak3_caesar_cipher;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CaesarServer {

    public static final int PORT = 5555;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            try {
                while (true) {
                    Socket client = server.accept();
                    CaesarServerWorker worker = new CaesarServerWorker(client);
                    new Thread(worker).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
