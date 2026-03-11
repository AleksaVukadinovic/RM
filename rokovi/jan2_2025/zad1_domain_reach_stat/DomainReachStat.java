package zad1_domain_reach_stat;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;


public class DomainReachStat {

    public final static String PATH_IN = "rokovi/jan2_2025/zad1_domain_reach_stat/domains.txt";
    public final static String PATH_OUT = "rokovi/jan2_2025/zad1_domain_reach_stat/reachable_stats.txt";

    public static void main(String[] args) {
        List<String> domains = new ArrayList<>();
        List<String[]> reachableDomains = new ArrayList<>(); // [domain, responseTime]
        int unreachableCount = 0;

        // Učitavanje domena iz fajla
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_IN))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    domains.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Greška pri čitanju domains.txt: " + e.getMessage());
            return;
        }

        System.out.println("Proveravam dostupnost " + domains.size() + " domena...\n");

        // Provjera dostupnosti svakog domena
        for (String domain : domains) {
            System.out.print("Proveravam: " + domain + " ... ");
            long startTime = System.currentTimeMillis();
            boolean reachable = false;

            try {
                InetAddress address = InetAddress.getByName(domain);
                reachable = address.isReachable(3000);
            } catch (IOException e) {
                reachable = false;
            }

            long responseTime = System.currentTimeMillis() - startTime;

            if (reachable) {
                reachableDomains.add(new String[]{domain, String.valueOf(responseTime)});
                System.out.println("DOSTUPAN (" + responseTime + " ms)");
            } else {
                unreachableCount++;
                System.out.println("NEDOSTUPAN");
            }
        }

        // Sortiranje dostupnih domena po vremenu odziva (uzlazno)
        reachableDomains.sort(Comparator.comparingLong(a -> Long.parseLong(a[1])));

        int reachableCount = reachableDomains.size();

        // Pisanje rezultata u reachable_stats.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_OUT))) {
            writer.write("Ukupan broj dostupnih domena: " + reachableCount);
            writer.newLine();
            writer.write("Ukupan broj nedostupnih domena: " + unreachableCount);
            writer.newLine();

            writer.write("Dostupni domeni sa vremenom odziva:");
            writer.newLine();
            for (String[] entry : reachableDomains) {
                writer.write(entry[0] + " - " + entry[1] + " ms");
                writer.newLine();
            }

            writer.write("Top 3 najbrža domena:");
            writer.newLine();
            int top = Math.min(3, reachableDomains.size());
            for (int i = 0; i < top; i++) {
                writer.write((i + 1) + ". " + reachableDomains.get(i)[0] + " - " + reachableDomains.get(i)[1] + " ms");
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Greška pri pisanju reachable_stats.txt: " + e.getMessage());
            return;
        }

        System.out.println("\nRezultati upisani u reachable_stats.txt");
        System.out.println("Dostupnih: " + reachableCount + " | Nedostupnih: " + unreachableCount);
    }
}