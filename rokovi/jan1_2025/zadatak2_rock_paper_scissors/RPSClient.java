package zadatak2_rock_paper_scissors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RPSClient {
    public static final int PORT = 5555;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()){
            System.out.print("Enter your name: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.next();

            byte[] buffer = new byte[BUFF_SIZE];
            DatagramPacket request = new DatagramPacket(
                    name.getBytes(),
                    name.length(),
                    InetAddress.getByName("localhost"),
                    PORT
            );

            socket.send(request);

            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            String reponse = new String(
                    responsePacket.getData(),
                    responsePacket.getOffset(),
                    responsePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.print(reponse);
            String move = scanner.next();

            DatagramPacket movePacket = new DatagramPacket(
                    move.getBytes(),
                    move.length(),
                    InetAddress.getByName("localhost"),
                    PORT
            );

            socket.send(movePacket);

            DatagramPacket matchResult = new DatagramPacket(buffer, buffer.length);
            socket.receive(matchResult);

            System.out.println(new String(
                    matchResult.getData(),
                    matchResult.getOffset(),
                    matchResult.getLength(),
                    StandardCharsets.UTF_8
            ));
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
