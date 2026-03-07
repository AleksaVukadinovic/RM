package jan1_2025_zad1;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

public class MainLoseResenje {

    private static final String IN_PATH = "3/jan1_2025_zad1/domains.txt";
    private static final String OUT_PATH = "3/jan1_2025_zad1/ip.with.domains.txt";

    public static void main(String[] args) throws IOException {
        String[] domains = readDomains();
        InetAddress[] addresses = getAddressesForDomains(domains);
        try (
                FileWriter fileWriter = new FileWriter(OUT_PATH);
                BufferedWriter writer = new BufferedWriter(fileWriter);
        ) {
            for (InetAddress address : addresses)
                showAddressDomains(address, domains, writer);
        }
        System.out.println("Writing to file successful!");
    }

    private static String[] readDomains() {
        ArrayList<String> domains = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(IN_PATH);
                BufferedReader reader = new BufferedReader(fileReader);
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                domains.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domains.toArray(new String[0]);
    }

    private static InetAddress[] getAddressesForDomains(String[] domains) throws UnknownHostException {
        ArrayList<InetAddress> addresses = new ArrayList<>();
        for (String domain : domains) {
            addresses.add(domainToAddress(domain));
        }
        return addresses.toArray(new InetAddress[0]);
    }

    private static InetAddress domainToAddress(String domain) throws UnknownHostException {
        return InetAddress.getByName(domain);
    }

    private static void showAddressDomains(InetAddress address, String[] domains, BufferedWriter writer) {
        try {
            writer.write(address.toString().split("/")[1] + "\n");
            writer.write("- Domains:\n");
            for (String domain : domains) {
                if (Objects.equals(address.getHostName(), domain)) {
                    writer.write("--- " + domain + "\n");
                }
            }
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
