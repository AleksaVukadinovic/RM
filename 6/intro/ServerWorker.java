package intro;

import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable{
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
        // Do something ...
    }
}
