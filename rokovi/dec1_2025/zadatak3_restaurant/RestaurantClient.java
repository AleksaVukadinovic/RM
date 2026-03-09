package zadatak3_restaurant;

import com.sun.security.jgss.GSSUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestaurantClient {
    public static final int PORT = 6000;
    private static final String HOST = "localhost";
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(); Scanner scanner = new Scanner(System.in)) {
            String line = scanner.nextLine();
            byte[] requestBytes = line.getBytes(StandardCharsets.UTF_8);

            DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length, InetAddress.getByName(HOST), PORT);
            socket.send(requestPacket);

            byte[] responseBytes = new byte[BUFF_SIZE];
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8);            System.out.println(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
