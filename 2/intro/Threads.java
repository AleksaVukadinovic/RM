package intro;

public class Threads {

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread());
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable: " + Thread.currentThread());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new MyThread();
        t1.start();

        Runnable r = new MyRunnable();
        Thread t2 = new Thread(r);
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Forcefully stopped threads");
        }

        System.out.println("Main: " + Thread.currentThread());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
