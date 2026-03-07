package HeaderPrinter;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class HeaderPrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                if (line.equalsIgnoreCase("exit")) break;

                URL url = new URL(line);
                URLConnection urlConnection = url.openConnection();
                printHeaderFields(urlConnection);
            } catch (IOException e) {
                System.err.println("Error opening the connection!");
            }
        }
    }

    private static void printHeaderFields(URLConnection urlConnection) {
        System.out.println("----------------------------------");
        System.out.println("Oth header: " + urlConnection.getHeaderField(0));

        for (int i = 1; ;i++) {
            String header = urlConnection.getHeaderField(i);
            if (header == null) break;
            System.out.println(urlConnection.getHeaderField(i) + ": " + header);
        }

        System.out.println("----------------------------------");
    }
}
