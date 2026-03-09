package zadatak2_chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ChessServer {
    public static final int PORT = 5000;
    public static final AtomicInteger nextId = new AtomicInteger(1);
    public static final ConcurrentHashMap<Integer, ChessPlayer> scoreboard = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server pokrenut na portu " + PORT);

            while (true) {
                try {
                    Socket socket = server.accept();
                    ChessServerThread clientHandler = new ChessServerThread(socket);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    System.err.println("Greska pri prihvatanju konekcije: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server nije mogao da se pokrene: " + e.getMessage());
        }
    }
}