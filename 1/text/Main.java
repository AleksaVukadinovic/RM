package text;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String IN_PATH = "1/text/in.txt";
    private static final String OUT_PATH = "1/text/out.txt";
    private static final int BUFF_SIZE = 512;

    public static void main(String[] args) {
        try (
                FileInputStream fileInputStream = new FileInputStream(IN_PATH);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);

                FileOutputStream fileOutputStream = new FileOutputStream(OUT_PATH);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        ) {
            char[] buffer = new char[BUFF_SIZE];
            int bytesRead = 0;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedWriter.write(buffer, 0, bytesRead);
            }
            System.out.println("Success!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
