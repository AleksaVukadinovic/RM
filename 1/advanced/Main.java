package advanced;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private final static String PATH = "1/advanced/file.txt";

    public static void main(String[] args) throws FileNotFoundException {
        try (
                PrintStream printStream = new PrintStream(
                        new BufferedOutputStream(
                                Files.newOutputStream(Paths.get(PATH))), true);
        ) {
            printStream.println("Hello World!");
            printStream.println(250);
            printStream.println(true);
            printStream.print("Great! ");
            printStream.println(3.2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scanner = new Scanner(new FileInputStream(PATH))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOError e) {
            throw new RuntimeException(e);
        }
    }
}