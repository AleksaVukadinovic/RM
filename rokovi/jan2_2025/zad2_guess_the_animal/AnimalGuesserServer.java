package zad2_guess_the_animal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AnimalGuesserServer {
    public static final int PORT = 5555;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                AnimalGuesserServerWorker worker = new AnimalGuesserServerWorker(client);
                new Thread(worker).start();
            }
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }
}
