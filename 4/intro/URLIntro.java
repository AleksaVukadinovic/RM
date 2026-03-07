package intro;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class URLIntro {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.matf.bg.ac.rs/studije/osnovne-studije/osnovne-informatika/");
            URL urlFull = new URL("ftp://mi23735@alas.matf.bg.ac.rs:300/materijali/rm.pdf?verzija=2#cas4");

            printInfo(url);
            printInfo(urlFull);

            System.out.println(InetAddress.getByName(url.getHost()));
            System.out.println(InetAddress.getByName(urlFull.getHost()));

            URLConnection urlConnection = url.openConnection();
            System.out.println(urlConnection.getHeaderFields());

            try (Scanner scanner = new Scanner(new BufferedInputStream(urlConnection.getInputStream()))) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("URL Error");
        } catch (UnknownHostException e) {
            System.err.println("Inet Error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printInfo(URL url) {

    }
}
