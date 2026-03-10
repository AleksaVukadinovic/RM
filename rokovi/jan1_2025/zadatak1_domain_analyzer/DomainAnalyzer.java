package zadatak1_domain_analyzer;

import java.io.*;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;

public class DomainAnalyzer {
    private static final String PATH_IN = "rokovi/jan1_2025/zadatak1_domain_analyzer/domains.txt";
    private static final String PATH_OUT = "rokovi/jan1_2025/zadatak1_domain_analyzer/ip.with.domains.txt";

    public static void main(String[] args) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                              new FileInputStream(PATH_IN)
                        )
                );
                
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(PATH_OUT)
                        )
                );
                ) {

            HashMap<String, LinkedList<String>> addressToDomains = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String ip = InetAddress.getByName(line).getHostAddress();
                String name = InetAddress.getByName(line).getHostName();
                if (addressToDomains.containsKey(ip)) {
                    LinkedList<String> tmp = addressToDomains.get(ip);
                    tmp.add(name);
                    addressToDomains.remove(ip);
                    addressToDomains.put(ip, tmp);
                } else {
                    LinkedList<String> names = new LinkedList<>();
                    names.add(name);
                    addressToDomains.put(ip, names);
                }
            }


            String[] ipAddresses = addressToDomains.keySet().toArray(new String[0]);
            for (String ipAddress : ipAddresses) {
                writer.write(ipAddress + '\n');
                writer.write("- Domains:" + '\n');
                LinkedList<String> names = addressToDomains.get(ipAddress);
                for (String name : names) {
                    writer.write("--- " + name + '\n');
                }
                writer.write('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
