package dec_ps_2025_zad2;

import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {
    private final Socket client;

    ServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serve();
        if (client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("[" + client + "]: Connection error");
            }
        }
    }

    private void serve() {

    }
}
