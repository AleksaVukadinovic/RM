package zadatak1_file_reader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLFileReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String path = scanner.nextLine();

            try {
                URL fileUrl = new URL("file://" + path);
                URLConnection connection = fileUrl.openConnection();
                System.out.println("Otvaram fajl pomocu \"file\" protokola:");

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("\t" + line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
