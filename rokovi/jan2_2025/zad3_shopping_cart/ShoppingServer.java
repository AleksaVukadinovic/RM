package zad3_shopping_cart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;

public class ShoppingServer {
    public static final int PORT = 5555;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT);) {
            byte[] buffer = new byte[BUFF_SIZE];

            DatagramPacket namePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(namePacket);

            String name = new String(
                    namePacket.getData(),
                    namePacket.getOffset(),
                    namePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            ShoppingItem[] items = {
                    new ShoppingItem("bread", 1),
                    new ShoppingItem("milk", 1.5),
                    new ShoppingItem("apple", 0.7),
                    new ShoppingItem("eggs", 2)
            };

            StringBuilder sb = new StringBuilder();
            sb.append("Welcome ").append(name).append("!\n");
            sb.append("Available groceries:\n");
            for (ShoppingItem item : items) {
                sb.append("- ").append(item.getName()).append(" -- ").append(item.getPrice()).append("$\n");
            }
            String initialResponse = sb.toString();

            DatagramPacket initialReponsePacket = new DatagramPacket(
                    initialResponse.getBytes(),
                    initialResponse.length(),
                    namePacket.getAddress(),
                    namePacket.getPort()
            );

            socket.send(initialReponsePacket);

            HashMap<String, LinkedList<ShoppingItem>> customers = new HashMap<>();

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);
                String request = new String(
                        requestPacket.getData(),
                        requestPacket.getOffset(),
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                ).trim();

                String[] commandAndArguments = request.split(" ", 2);
                String command = commandAndArguments[0].toUpperCase();

                customers.putIfAbsent(name, new LinkedList<>());
                LinkedList<ShoppingItem> cart = customers.get(name);

                HashMap<String, Double> stock = new HashMap<>();
                for (ShoppingItem item : items) {
                    stock.put(item.getName().toLowerCase(), item.getPrice());
                }

                String response;
                if (command.equals("ADD") && commandAndArguments.length > 1) {
                    String itemName = commandAndArguments[1].toLowerCase();
                    if (stock.containsKey(itemName)) {
                        cart.add(new ShoppingItem(itemName, stock.get(itemName)));
                        response = "Added to cart: " + itemName;
                    } else {
                        response = "Error: Item not found!";
                    }
                } else if (command.equals("REMOVE") && commandAndArguments.length > 1) {
                    String itemName = commandAndArguments[1].toLowerCase();
                    boolean removed = cart.removeIf(i -> i.getName().equalsIgnoreCase(itemName));
                    response = removed ? "Removed from cart: " + itemName : "Error: Item not in cart!";
                } else if (command.equals("VIEW")) {
                    double total = cart.stream().mapToDouble(ShoppingItem::getPrice).sum();
                    response = "Cart: " + cart.stream().map(ShoppingItem::getName).toList() + " Total: " + total + "$";
                } else if (command.equals("PAY")) {
                    double total = cart.stream().mapToDouble(ShoppingItem::getPrice).sum();
                    response = "Total: " + total + "$";
                    cart.clear();
                } else {
                    response = "Error: Invalid command!";
                }

                DatagramPacket responsePacket = new DatagramPacket(
                        response.getBytes(),
                        response.length(),
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
