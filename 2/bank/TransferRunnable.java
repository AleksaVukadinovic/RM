package bank;

import java.util.concurrent.ThreadLocalRandom;

public class TransferRunnable implements Runnable {

    private IBank bank;
    private int from;
    private int max;

    private static final int SLEEP_DURATION = 2;

    public TransferRunnable(int max, int from, IBank bank) {
        this.max = max;
        this.from = from;
        this.bank = bank;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            while (true) {
                int to = r.nextInt(this.bank.clientCount());
                int amount = r.nextInt(this.max);
                this.bank.transfer(this.from, to, amount);
                Thread.sleep(SLEEP_DURATION);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
