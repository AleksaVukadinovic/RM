package intro;

import java.io.*;
import java.net.Socket;

public class ClientSocketIntro {
    // HTTP, HTTPS <=> TCP

    public static void main(String[] args) {
        try (
            Socket socket = new Socket("www.matf.bg.ac.rs", 80);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    )
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );
        ) {
            System.out.println(socket);
            System.out.println(socket.getPort());
            System.out.println(socket.getInetAddress());
            System.out.println(socket.getLocalPort());
            System.out.println(socket.getLocalAddress());

            writer.write("GET / HTTP/1.1\r\n" +
                    "Host: www.matf.bg.ac.rs\r\n" +
                    "Accept: text/html\r\n" +
                    "Connection: keep-alive\r\n" +
                    "\r\n"
            );
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
