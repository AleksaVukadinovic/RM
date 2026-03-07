package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {
    public static final int PORT = 90000;
    public static final Set<ChatServerWorker> clients = Collections.synchronizedSet(new HashSet<>());

    public static void broadcast(ChatServerWorker senderWorker, String message) {
        synchronized (clients) {
            for (ChatServerWorker worker : clients) {
                if (worker.equals(senderWorker))
                    continue;
                worker.sendMessage(message);
            }
        }
    }

    public static List<String> getUsernames() {
        List<String> usernames = new LinkedList<String>();
        synchronized (clients) {
            for(ChatServerWorker worker: clients) {
                usernames.add(worker.getUsername());
            }
        }
        return usernames;
    }

    public static void remove(ChatServerWorker worker) {
        clients.remove(worker);
    }

    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(PORT);
        ) {
            while (true) {
                Socket client = server.accept();
                System.out.println("New person has joined!");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
