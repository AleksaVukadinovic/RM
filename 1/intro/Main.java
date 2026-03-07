package intro;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        OutputStream out;

        try (
                FileOutputStream fout = new FileOutputStream("1/intro/file.txt");
                BufferedOutputStream bfout = new BufferedOutputStream(fout);
        ) {
            bfout.write(97); // writes 'a', 97 is ascii for 'a'
            bfout.write(new byte[]{97, 98, 99}, 1, 2); // writes 'b' (98) anc 'c' (99(
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("IO Error!");
        }

        InputStream in;

        try (
                FileInputStream fin = new FileInputStream("1/intro/file.txt");
                BufferedInputStream bfin = new BufferedInputStream(fin);
        ) {
            int b = bfin.read();
            System.out.println(b);
            byte[] read = new byte[2];
            int bb = bfin.read(read);
            for (int i = 0; i < bb; i++) {
                System.out.println(read[i]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("IO Error!");
        }

        try (
                FileWriter fout = new FileWriter("1/intro/file-raw.txt");
                BufferedWriter bfout = new BufferedWriter(fout);
        ) {
            bfout.write('a'); // a
            bfout.write(new char[]{'a', 'b', 'c'}, 1, 2); // bc
            bfout.write("def"); // def
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("IO Error!");
        }

        try (
                FileReader fin = new FileReader("1/intro/file-raw.txt");
                BufferedReader bfin = new BufferedReader(fin);
        ) {
            char[] buf = new char[6];
            int len = bfin.read(buf);
            System.out.println("Buffer length is: " + len);
            System.out.println(buf);
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("IO Error!");
        }
    }
}