package ResourceGetter;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextFileGetter {
    private static final String TEXTUAL_FILE_URL = "https://poincare.matf.bg.ac.rs/~milan.bankovic/preuzimanje/ar/pravila_igre.txt";

    public static void main(String[] args) {
        try {
            URL url = new URL(TEXTUAL_FILE_URL);
            URLConnection urlConnection = url.openConnection();

            String contentType = urlConnection.getContentType();
            String encoding = urlConnection.getContentEncoding();

            System.out.println(contentType);
            System.out.println(encoding);

            if (!contentType.startsWith("text")) {
                System.err.println("Error: Not a textual file");
                return;
            }

            if (encoding == null) {
                encoding = "UTF-8";
            }

            String fileName = url.getFile();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            System.out.println(fileName);

            try (
                    Scanner scanner = new Scanner(new BufferedInputStream(urlConnection.getInputStream()));
                    PrintStream writer = new PrintStream(Files.newOutputStream(Paths.get("4/ResourceGetter" + fileName)), true, encoding)
            ) {
                while (scanner.hasNextLine()) {
                    writer.println(scanner.nextLine());
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR!");
        }
    }
}
