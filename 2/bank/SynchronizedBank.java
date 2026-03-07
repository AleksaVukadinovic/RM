package bank;

import java.util.Arrays;

public class SynchronizedBank implements IBank {
    private final int[] accounts;


    SynchronizedBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
    }

    @Override
    public void transfer(int from, int to, int amount) {
        synchronized (this) {
            while (this.accounts[from] < amount) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            this.accounts[from] -= amount;
            this.accounts[to] += amount;
            this.notifyAll();
        }

        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + getTotalBalance());
        System.out.println();
    }

    @Override
    public int getTotalBalance() {
        int total = 0;
        for (int i = 0; i < this.clientCount(); i++)
            total += this.accounts[i];
        return total;
    }

    @Override
    public int clientCount() {
        return this.accounts.length;
    }
}
