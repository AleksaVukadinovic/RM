package sep2_2025_zad3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OsmosmerkaServer {
    public static final int PORT = 5555;
    private final static String PATH = "8/sep2_2025_zad3/osmosmerka.txt";

    public static void main(String[] args) {
        Osmosmerka osmosmerka;
        try {
            osmosmerka = new Osmosmerka(PATH);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to parse file!");
            return;
        }

        try (ServerSocket sever = new ServerSocket(PORT)) {
            Socket client;
            while (true) {
                client = sever.accept();
                System.err.println("New client joined.");
                new Thread(new OsmosmerkaThread(client, osmosmerka)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
