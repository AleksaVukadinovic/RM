package Chat;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ChatClientListener implements Runnable{
    private Scanner scanner;
    private String username;

    public ChatClientListener(InputStream inputStream, String username) {
        this.scanner = new Scanner(
                new BufferedInputStream(
                        inputStream
                )
        );
        this.username = username;
    }

    private void serve() {
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    @Override
    public void run() {
        serve();
        if (scanner != null) {
            scanner.close();
        }
    }
}
