package bank;

import java.util.Arrays;

public class DefaultBank implements IBank {

    private int[] accounts;

    DefaultBank(int numberOfAccounts, int initialBalance) {
        this.accounts = new int[numberOfAccounts];
        Arrays.fill(this.accounts, initialBalance);
    }

    @Override
    public void transfer(int from, int to, int amount) {
        if (this.accounts[from] < amount)
            return;

        this.accounts[from] -= amount;
        this.accounts[to] += amount;
        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + this.getTotalBalance() + "\n");
    }

    @Override
    public int getTotalBalance() {
        int total = 0;
        for (int acc : this.accounts) {
            total += acc;
        }
        return total;
    }

    @Override
    public int clientCount() {
        return this.accounts.length;
    }
}
