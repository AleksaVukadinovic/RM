package bank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank implements IBank {

    private int[] accounts;
    private Lock lock;
    private Condition insufficientFunds;

    LockedBank(int numberOfAccounts, int initialBalance) {
        this.accounts = new int[numberOfAccounts];
        Arrays.fill(this.accounts, initialBalance);
        this.lock = new ReentrantLock();
        this.insufficientFunds = this.lock.newCondition();
    }

    @Override
    public void transfer(int from, int to, int amount) {
        int total;
        this.lock.lock();
        try {
            while (this.accounts[from] < amount) {
                try {
                    this.insufficientFunds.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            this.accounts[from] -= amount;
            this.accounts[to] += amount;
            total = this.getTotalBalance();

        } finally {
            this.lock.unlock();
        }

        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + total);
        System.out.println();

    }

    @Override
    public int getTotalBalance() {
        int total = 0;
        for (int acc : this.accounts)
            total += acc;
        return total;
    }

    @Override
    public int clientCount() {
        return this.accounts.length;
    }
}
