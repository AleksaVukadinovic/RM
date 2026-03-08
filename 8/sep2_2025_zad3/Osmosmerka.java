package sep2_2025_zad3;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Osmosmerka {
    private final char[][] letters;
    private final List<String> words;

    public Osmosmerka(String filePath) {
        this.letters = new char[8][];
        this.words = new ArrayList<>();

        try (Scanner scanner = new Scanner(
            new BufferedInputStream(
                new FileInputStream(
                    filePath
                )
            )
        )) {
            for (int i = 0; i < 8; i++) {
                letters[i] = scanner.nextLine().toCharArray();
            }
            this.words.addAll(Arrays.asList(scanner.nextLine().split("\\|")));
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }
}
