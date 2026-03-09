package zadatak3_restaurant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RestaurantServer {
    public static final int PORT = 6000;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        HashMap<String, String> reservations = new HashMap<>();

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                byte[] buffer = new byte[BUFF_SIZE];

                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);

                String request = new String(receivedPacket.getData(), 0, receivedPacket.getLength(), StandardCharsets.UTF_8).trim();                String[] nameAndTime = request.split(" ");
                String name = nameAndTime[0];
                String time = nameAndTime[1];

                String response;
                if (reservations.containsKey(time)) {
                    response = "Vec postoji rezervacija od strane \" " + reservations.get(time) + "\" u to vreme, molimo pokusajte kasnije!";
                } else {
                    reservations.put(time, name);
                    response = "Uspesno ste rezervisali mesto za \"" + name +"\" u " + time;
                }

                DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.length(), receivedPacket.getAddress(), receivedPacket.getPort());
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
