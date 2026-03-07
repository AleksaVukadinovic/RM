package NumberGuesser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ServerThread implements Runnable{
    private final Socket client;
    private final int numberToGuess;

    public ServerThread(Socket client) {
        this.numberToGuess = ThreadLocalRandom.current().nextInt(1, 101);
        this.client = client;
    }
    
    
    private void serve() {
        try (
                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                client.getOutputStream()
                        ), true
                );

                Scanner reader = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                );
        ) {
            writer.println("Pogodi koji broj od 1 do 100 sam zamislio");
            while (reader.hasNextInt()) {
                int clientGuess = reader.nextInt();
                if (clientGuess == numberToGuess) {
                    writer.println("Čestitam! Pogodili ste broj.");
                    break;
                } else if (clientGuess < numberToGuess) {
                    writer.println("Zamišljeni broj je veći od toga");
                } else {
                    writer.println("Zamišljeni broj je manji od toga");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }    
    }
    
    @Override
    public void run() {
        serve();
        if (client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
