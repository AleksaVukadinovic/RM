package datetime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UDPDateTimeServer {

    private static final int PORT = 5000;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] responseBytes;
            byte[] buff = new byte[BUFF_SIZE];

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buff, buff.length);
                socket.receive(requestPacket);

                String option = new String(
                        requestPacket.getData(),
                        0,
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                LocalDateTime now = LocalDateTime.now();

                String response;
                switch (option) {
                    case "DATE":
                        response = now.toLocalDate().toString();
                        break;
                    case "TIME":
                        response = now.toLocalTime().toString();
                        break;
                    case "DATETIME":
                        response = now.toString();
                        break;
                    default:
                        response = "ERROR!";
                        break;
                }

                responseBytes = response.getBytes(StandardCharsets.US_ASCII);
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
