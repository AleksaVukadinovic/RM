package PortScanner;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class PortScanner {
    public static void main(String[] args) {
        String host = "hypatia.matf.bg.ac.rs";
        for (int port = 10333; port <= 65535; port++) {
            System.err.println("TESTING PORT: " + port);
            try (
                    Socket socket = new Socket(host, port)
            ) {
                System.err.println("\tSocket data: " + socket);
                System.err.println("\tFound: " + new Date());
            } catch (IOException e) {
                System.err.println("No one is here...");
            }
        }
    }
}
