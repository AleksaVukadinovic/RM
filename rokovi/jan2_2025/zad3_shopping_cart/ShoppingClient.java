package zad3_shopping_cart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ShoppingClient {
    private static final int PORT = 5555;
    private static final String HOST = "localhost";
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {

            byte[] buffer = new byte[BUFF_SIZE];

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            DatagramPacket namePacket = new DatagramPacket(
                    name.getBytes(),
                    name.length(),
                    InetAddress.getByName(HOST),
                    PORT
            );

            socket.send(namePacket);

            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            String initResponse = new String(
                    responsePacket.getData(),
                    responsePacket.getOffset(),
                    responsePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.println(initResponse);

            while (true) {
                String request = scanner.nextLine();
                DatagramPacket commandPacket = new DatagramPacket(
                        request.getBytes(),
                        request.length(),
                        InetAddress.getByName(HOST),
                        PORT
                );
                socket.send(commandPacket);

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                String response = new String(
                        datagramPacket.getData(),
                        datagramPacket.getOffset(),
                        datagramPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println(response);

                if (request.equals("PAY")) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
