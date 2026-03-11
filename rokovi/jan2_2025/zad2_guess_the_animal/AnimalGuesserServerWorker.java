package zad2_guess_the_animal;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class AnimalGuesserServerWorker implements Runnable {
    private final Socket client;

    public AnimalGuesserServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("POKRENUT RUN");
        serve();
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void serve() {
        String PATH = "rokovi/jan2_2025/zad2_guess_the_animal/animals.txt";
        try (
                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                client.getOutputStream()
                        ), true
                );

                BufferedReader fileReader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        PATH
                                )
                        )
                );

                Scanner reader = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                )
                ) {
            writer.println("Welcome to server for guessing the animal!");
            ArrayList<String> animals = new ArrayList<>();
            String line;
            while ( (line = fileReader.readLine()) != null) {
                animals.add(line);
            }
            writer.println("Available animals are: " + animals);
            writer.println("Enter your name: ");
            String name = reader.nextLine();

            Random random = new Random();
            String answer = animals.get(random.nextInt(animals.size()));
            writer.println(name + ", guess the animal I chose:");
            while (true) {
                writer.print("Your guess: ");
                String guess = reader.next();
                if (guess.equalsIgnoreCase(answer)) {
                    writer.println("Server: correct!");
                    break;
                } else {
                    if (guess.compareTo(answer) < 0) {
                        writer.println("Server: Too early in alphabet");
                    } else {
                        writer.println("Server: Too late in alphabet");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
