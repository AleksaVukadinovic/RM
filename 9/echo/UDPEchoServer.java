package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPEchoServer {
    public static final int PORT = 5000;
    public static final int BUFF_SIZE = 2048;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[BUFF_SIZE];
            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                String request = new String(
                        requestPacket.getData(),
                        0,
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                String response = request;
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes,
                        responseBytes.length,
                        requestPacket.getAddress(),
                        requestPacket.getPort()
                );

                socket.send(responsePacket);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
