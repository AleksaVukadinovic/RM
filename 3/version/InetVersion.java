package version;

import java.net.*;

public class InetVersion {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address1 = InetAddress.getByName("google.com");
        System.out.println(address1);
        System.out.println(getVersion(address1));
        printAddress(address1.getAddress());
    }

    private static String getVersion(InetAddress address) {
        int size = address.getAddress().length;
        if (size == 4) return "IPv4";
        else if (size == 16) return "IPv6";
        else return "Unknown";
    }

    private static void printAddress(byte[] address) {
        System.out.print("Bajtovi IP adrese su: ");
        for(byte b : address) {
            int unsignedByte = b < 0 ? b + 256 : b;
            System.out.print(unsignedByte + " ");
        }
        System.out.println();
    }
}
