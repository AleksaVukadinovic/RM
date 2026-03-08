package dec_ps_2025_zad2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessServer {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            try (
                Socket socket = server.accept();

                PrintStream writer = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ), true
                );

                Scanner networkScanner = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()
                        )
                );
            ) {
                System.out.println("Server is active and listening on PORT: " + PORT);
                int idCounter = 1;
                List<ChessPlayer> players = new ArrayList<>();

                while (networkScanner.hasNextLine()) {
                    String line = networkScanner.nextLine();
                    if (line.toLowerCase().contains("bye")) {
                        writer.println("Bye!");
                        server.close();
                        break;
                    }

                    String[] commandAndArguments = line.split(" ");
                    String command = commandAndArguments[0];
                    if (command.equalsIgnoreCase("select")) {
                        if (commandAndArguments.length != 2) {
                            writer.println("Wrong number of arguments for select command!");
                            writer.println("Usage: select <id>");
                        }

                        int id = Integer.parseInt(commandAndArguments[1]);
                        for (ChessPlayer player : players) {
                            if (player.getId() == id) {
                                writer.println(player.getName() + " : " + player.getElo());
                                break;
                            }
                        }

                    } else if (command.equalsIgnoreCase("insert")) {
                        if (commandAndArguments.length < 2) {
                            writer.println("Wrong number of arguments for insert command!");
                            writer.println("Usage: insert <name>");
                        }

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 1; i < commandAndArguments.length; i++) {
                            nameBuilder.append(commandAndArguments[i]).append(i == commandAndArguments.length - 1 ? "" : " ");
                        }
                        String name = nameBuilder.toString();
                        players.add(new ChessPlayer(idCounter++, name, 1300));
                        writer.println("insert je uspesno izvrsen");
                    } else if (command.equalsIgnoreCase("update")) {
                        if (commandAndArguments.length != 3) {
                            writer.println("Wrong number of arguments for update command!");
                            writer.println("Usage: update <id> <elo>");
                        }

                        int id = Integer.parseInt(commandAndArguments[1]);
                        int elo = Integer.parseInt(commandAndArguments[2]);
                        for (ChessPlayer player : players) {
                            if (player.getId() == id) {
                                player.setElo(elo);
                                writer.println("update je uspesno izvrsen");
                                break;
                            }
                        }
                    } else {
                        writer.println("Unknown command: " + command);
                        writer.println("Available commands are select, insert, update and bye");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
