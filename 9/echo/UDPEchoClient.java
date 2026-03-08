package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPEchoClient {
    public static final String HOST = "localhost";
    public static final int PORT = 5000;
    public static final int BUFF_SIZE = 2048;

    public static void main(String[] args) {
        try (
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
        ) {
            byte[] buff = new byte[BUFF_SIZE];
            byte[] messageBytes;

            while (true) {
                System.out.println("Enter line: ");

                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) break;

                messageBytes = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket requestPacket = new DatagramPacket(
                        messageBytes, messageBytes.length, InetAddress.getByName(HOST), PORT
                );
                socket.send(requestPacket);

                DatagramPacket responsePacket = new DatagramPacket(buff, buff.length);
                socket.receive(responsePacket);

                String response = new String(
                        responsePacket.getData(),
                        0,
                        responsePacket.getLength(),
                        StandardCharsets.UTF_8
                );
                System.out.println("ECHO: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
