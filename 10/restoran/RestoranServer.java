package restoran;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RestoranServer {

    public static final int PORT = 6000;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        HashMap<String, String> reservations = new HashMap<>();

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                byte[] buffer = new byte[BUFF_SIZE];

                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                String request = new String(
                        requestPacket.getData(),
                        requestPacket.getOffset(),
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                String[] nameAndTime = request.split(" ");
                String name = nameAndTime[0];
                String time = nameAndTime[1];

                String response;
                if (reservations.containsKey(time)) {
                    response = "Vec postoji rezervacija od strane \"" + reservations.get(time) + "\" u to vreme, molimo pokusajte kasnije!";
                } else {
                    reservations.put(time, name);
                    response = "Uspesno ste rezervisali mesto za \"" + name + "\" u " + time;
                }

                byte[] responsePacket = response.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(responsePacket, responsePacket.length, requestPacket.getAddress(), requestPacket.getPort()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
