package restoran;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestoranClient {
    private static final String HOST = "localhost";
    private static final Integer BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in);
            ) {
            String request = scanner.nextLine();
            byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket requestPacket = new DatagramPacket(
                    requestBytes,
                    requestBytes.length,
                    InetAddress.getByName(HOST),
                    RestoranServer.PORT
            );

            socket.send(requestPacket);

            byte[] responseBytes = new byte[BUFF_SIZE];
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            socket.receive(responsePacket);

            String response = new String(
                    responsePacket.getData(),
                    responsePacket.getOffset(),
                    responsePacket.getLength(),
                    StandardCharsets.UTF_8
            );
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
