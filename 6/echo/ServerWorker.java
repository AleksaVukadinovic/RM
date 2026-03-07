package echo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerWorker implements Runnable {
    private final Socket client;

    public ServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serveClient();
        if (client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Communication error!");
            }
        }
    }

    private void serveClient() {
        try (
                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                this.client.getOutputStream()
                        ), true
                );
                Scanner reader = new Scanner(this.client.getInputStream());
        ) {
            while (reader.hasNextLine()) {
                writer.println(reader.nextLine());
            }
        } catch (IOException e) {
            System.err.println("ServerWorker::Communication error!");
        }
    }
}
