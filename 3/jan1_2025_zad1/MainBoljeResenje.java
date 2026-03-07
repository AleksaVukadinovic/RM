package jan1_2025_zad1;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainBoljeResenje {
    private static final String IN_PATH = "3/jan1_2025_zad1/domains.txt";
    private static final String OUT_PATH = "3/jan1_2025_zad1/ip.with.domains.txt";

    public static void main(String[] args) {
        Map<String, List<String>> ipToDomains = new HashMap<>();
        try (
            Scanner reader = new Scanner(Files.newInputStream(Paths.get(IN_PATH)));
            PrintStream writer = new PrintStream(new BufferedOutputStream(Files.newOutputStream(Paths.get(OUT_PATH))), true)
        )
        {
            while (reader.hasNextLine()) {
                String domain = reader.nextLine();

                try {
                    InetAddress address = InetAddress.getByName(domain);

                    if (!ipToDomains.containsKey(address.getHostAddress())) {
                        ipToDomains.put(address.getHostAddress(), new ArrayList<>());
                    }

                    List<String> current = ipToDomains.get(address.getHostAddress());
                    current.add(domain);
                    ipToDomains.put(address.getHostAddress(), current);
                } catch (UnknownHostException e) {
                    System.err.println("Can not access domain: " + domain);
                }
            }

            for (Map.Entry<String, List<String>> entry : ipToDomains.entrySet()) {
                writer.println(entry.getKey());
                writer.println("- Domains:");
                for (String domain : entry.getValue()) {
                    writer.print("--- ");
                    writer.println(domain);
                }
                writer.println();
            }

            System.out.println("Success!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
