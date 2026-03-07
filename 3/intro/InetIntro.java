package intro;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetIntro {
    public static void main(String[] args) {
        InetAddress inetAddress;
        Inet4Address ipv4;
        Inet6Address ipv6;

        try {
            InetAddress google = InetAddress.getByName("www.google.com");
            InetAddress random = InetAddress.getByName("208.201.239.101");

            System.out.println(google);
            System.out.println(random.getHostName());

            InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
            System.out.println(Arrays.toString(addresses));

            InetAddress special = InetAddress.getByName("localhost");
            System.out.println(special);

             System.out.println(Arrays.toString(InetAddress.getByName("www.matf.bg.ac.rs").getAddress()));
             System.out.println(InetAddress.getByName("www.matf.bg.ac.rs"));

            InetAddress ipv6addr = InetAddress.getByName("ipv6.google.com");
             System.out.println(ipv6addr.getHostName());
            // System.out.println(ipv6addr.getCanonicalHostName()); // SHOULDN'T BE USED
             System.out.println(ipv6addr.getHostAddress());

            InetAddress matfFull = InetAddress.getByName("www.matf.bg.ac.rs");
            InetAddress matfShort = InetAddress.getByName("www.math.rs");
            System.out.println(matfShort);
            System.out.println(matfFull);

            System.out.println(matfFull.equals(matfShort));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
