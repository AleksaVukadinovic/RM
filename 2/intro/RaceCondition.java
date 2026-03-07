package intro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceCondition {

    private static final AtomicInteger x = new AtomicInteger(0);
    private static final int LIMIT = 100000;

    private static class Test implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < LIMIT; i++) {
                x.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int THREAD_NUM = 10;

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < THREAD_NUM; i++) {
            Thread t = new Thread(new Test());
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: " + THREAD_NUM * LIMIT);
        System.out.println("Actual: " + x.get());
    }
}
