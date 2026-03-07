package ProtocolTester;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ProtocolTester {
    private static final String BASE_URL = "://www.example.com";

    public static void main(String[] args) {
        testProtocol("https" + BASE_URL);
        testProtocol("http" + BASE_URL);
        testProtocol("ftp" + BASE_URL);
        testProtocol("sftp" + BASE_URL);
        testProtocol("ssh" + BASE_URL);
        testProtocol("file" + BASE_URL);
        testProtocol("telnet" + BASE_URL);
        testProtocol("mailto:milan.mitreski@matf.bg.ac.rs");
    }

    private static void testProtocol(String url) {
        try {
            URL u = new URL(url);
            System.out.println("SUPPORTED:\t\t" + u.getProtocol());
        } catch (MalformedURLException e) {
            String protocol = url.split(":")[0];
            System.out.println("NOT SUPPORTED:\t" + protocol);
        }
    }
}
