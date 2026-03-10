package zadatak2_rock_paper_scissors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RPSServer {
    private enum Moves {
        ROCK,
        PAPER,
        SCISSORS
    }

    public static final int PORT = 5555;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)){
            try {
                byte[] buffer = new byte[BUFF_SIZE];

                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);

                String name = new String(
                        receivedPacket.getData(),
                        receivedPacket.getOffset(),
                        receivedPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                String response = name + ", what is your move: ";
                DatagramPacket responsePacket = new DatagramPacket(
                        response.getBytes(),
                        response.length(),
                        receivedPacket.getAddress(),
                        receivedPacket.getPort()
                );
                socket.send(responsePacket);

                Moves serverMove = Moves.values()[new Random().nextInt(Moves.values().length)];
                System.out.println(serverMove);

                DatagramPacket receivedPacket2 = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket2);

                String clientPlay = new String(
                        receivedPacket2.getData(),
                        receivedPacket2.getOffset(),
                        receivedPacket2.getLength(),
                        StandardCharsets.UTF_8
                ).trim().toUpperCase();

                String result;

                try {
                    Moves clientMove = Moves.valueOf(clientPlay);
                    if (clientMove == serverMove) {
                        result = "Draw! Both chose " + serverMove;
                    } else if ((clientMove == Moves.ROCK && serverMove == Moves.SCISSORS) ||
                            (clientMove == Moves.PAPER && serverMove == Moves.ROCK) ||
                            (clientMove == Moves.SCISSORS && serverMove == Moves.PAPER)) {
                        result = "Server chose " + serverMove + ". You win!";
                    } else {
                        result = "Server chose " + serverMove + ". You lose!";
                    }
                } catch (IllegalArgumentException e) {
                    result = "Invalid move! Server chose " + serverMove;
                }

                DatagramPacket resultPacket = new DatagramPacket(
                        result.getBytes(),
                        result.length(),
                        receivedPacket2.getAddress(),
                        receivedPacket2.getPort()
                );

                socket.send(resultPacket);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
