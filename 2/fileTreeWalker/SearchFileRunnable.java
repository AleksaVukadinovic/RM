package fileTreeWalker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SearchFileRunnable implements Runnable {

    private final BlockingQueue<Path> fileQueue;
    private final String keyword;

    SearchFileRunnable(String keyword, BlockingQueue<Path> fileQueue) {
        this.keyword = keyword;
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Path path = this.fileQueue.take();
                if (path.equals(FileTreeWalkerRunnable.END_OF_WORK)) {
                    this.fileQueue.put(path);
                    break;
                }
                this.search(path);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void search(Path file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            int lines = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines++;
                if (line.contains((this.keyword)))
                    System.out.println(file.getFileName() + ":" + lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
