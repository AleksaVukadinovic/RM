package image;

import java.io.*;

public class Main {
    private static final String IN_PATH = "1/image/in.PNG";
    private static final String OUT_PATH = "1/image/out.PNG";
    private static final int BUFFER_SIZE = 2048;

    public static void main(String[] args) {
        try (
                FileInputStream fileInputStream = new FileInputStream(IN_PATH);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                FileOutputStream fileOutputStream = new FileOutputStream(OUT_PATH);
                BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            while ((bytesRead =bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("Success!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}