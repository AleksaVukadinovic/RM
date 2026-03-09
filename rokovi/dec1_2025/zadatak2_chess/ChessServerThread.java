package zadatak2_chess;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChessServerThread implements Runnable {
    private final Socket socket;

    public ChessServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("client accepted " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

        serve();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serve() {
        try (
                PrintStream out = new PrintStream(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream())
        ) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.equalsIgnoreCase("bye")) break;

                String[] parts = line.split(" ", 2); // Odvajamo komandu od ostatka
                String command = parts[0].toLowerCase();

                try {
                    switch (command) {
                        case "insert":
                            handleInsert(parts, out);
                            break;
                        case "select":
                            handleSelect(parts, out);
                            break;
                        case "update":
                            handleUpdate(parts, out);
                            break;
                        default:
                            out.println("Greska: Nepoznata komanda.");
                    }
                } catch (Exception e) {
                    out.println("Greska: Nevalidan format komande.");
                }
            }
        } catch (IOException e) {
            System.err.println("Prekid veze sa klijentom.");
        }
    }

    private void handleInsert(String[] parts, PrintStream out) {
        if (parts.length < 2) {
            out.println("Greska: Nedostaje naziv.");
            return;
        }
        String name = parts[1];
        int newId = ChessServer.nextId.getAndIncrement();
        ChessServer.scoreboard.put(newId, new ChessPlayer(name));
        out.println("insert je uspesno izvrsen");
    }

    private void handleSelect(String[] parts, PrintStream out) {
        if (parts.length < 2) {
            out.println("Greska: Nedostaje ID.");
            return;
        }
        int id = Integer.parseInt(parts[1]);
        ChessPlayer player = ChessServer.scoreboard.get(id);
        if (player != null) {
            out.println(player.getName() + " : " + player.getElo());
        } else {
            out.println("Greska: Igrac sa ID " + id + " ne postoji.");
        }
    }

    private void handleUpdate(String[] parts, PrintStream out) {
        if (parts.length < 2) {
            out.println("Greska: Nedostaju parametri.");
            return;
        }
        String[] args = parts[1].split(" ");
        if (args.length < 2) {
            out.println("Greska: Nedostaje ELO vrednost.");
            return;
        }

        int id = Integer.parseInt(args[0]);
        int newElo = Integer.parseInt(args[1]);

        if (newElo < 1300) {
            out.println("elo vrednost iznosi barem 1300");
            return;
        }

        ChessPlayer updated = ChessServer.scoreboard.computeIfPresent(id, (k, player) -> {
            player.setElo(newElo);
            return player;
        });

        if (updated != null) {
            out.println("update je uspesno izvrsen");
        } else {
            out.println("Greska: Igrac sa ID " + id + " ne postoji.");
        }
    }
}