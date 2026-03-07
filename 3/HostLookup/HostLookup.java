package HostLookup;

import java.net.*;
import java.util.Scanner;

public class HostLookup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter names and IP addresses. Enter \"exit\" to quit.");

        while (true) {
            String host = scanner.nextLine();
            if (host.equalsIgnoreCase("exit"))
                break;
            System.out.println(lookup(host));
        }
    }

    private static String lookup(String host) {
        InetAddress node;
        try {
            node = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            return "Cannot find host: " + host;
        }

        return isHostname(host) ? node.getHostName() : node.getHostAddress();
    }

    private static boolean isHostname(String host) {
        if (host.contains(":")) return false;
        if (host.split("\\.").length != 4) return true;

        for (int i = 0; i < host.length(); i++) {
            char current = host.charAt(i);
            if (current != '.' && !Character.isDigit(current)) {
                return true;
            }
        }

        return false;
    }
}
