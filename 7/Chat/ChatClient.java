package Chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        try (
            Socket socket = new Socket("localhost", ChatServer.PORT);
        ) {
            Thread senderThread = new Thread(new ChatClientSender(socket.getOutputStream(), username));
            Thread listenerThread = new Thread(new ChatClientListener(socket.getInputStream(), username));

            senderThread.start();
            listenerThread.start();

            senderThread.join();
            listenerThread.join();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
