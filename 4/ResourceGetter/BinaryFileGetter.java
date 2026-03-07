package ResourceGetter;

import javax.imageio.IIOException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinaryFileGetter {
    private static final String BINARY_FILE_URL = "https://www.matf.bg.ac.rs/wp-content/uploads/uvod-u-informatiku.pdf";

    public static void main(String[] args) {
        try {
            URL url = new URL(BINARY_FILE_URL);
            URLConnection urlConnection = url.openConnection();

            String contentType = urlConnection.getContentType();
            int contentLength = urlConnection.getContentLength();
            String encoding = urlConnection.getContentEncoding();

            System.out.println(contentType);
            System.out.println(contentLength);
            System.out.println(encoding);

            if (contentLength == -1 || contentType.startsWith("text")) {
                System.err.println("File is not a binary");
                return;
            }

            String fileName = url.getFile();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            System.out.println(fileName);

            try (
                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get("4/ResourceGetter" + fileName)))
            ) {
                for (int i = 0; i < contentLength; i++) {
                    int b = in.read();
                    out.write(b);
                }
            } catch (IOException e) {
                System.err.println("ERROR!");
            }
        } catch (IOException e) {
            System.err.println("ERROR!");
        }
    }
}
