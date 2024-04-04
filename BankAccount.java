import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Deposited $" + amount);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrawn $" + amount);
            } else {
                System.out.println("Insufficient funds to withdraw $" + amount);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        return balance;
    }
}

class Transaction implements Runnable {
    private final BankAccount account;
    private final String type;
    private final int amount;

    public Transaction(BankAccount account, String type, int amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public void run() {
        switch (type) {
            case "deposit":
                account.deposit(amount);
                break;
            case "withdraw":
                account.withdraw(amount);
                break;
            default:
                System.out.println("Invalid transaction type");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        Thread thread1 = new Thread(new Transaction(account, "withdraw", 200));
        Thread thread2 = new Thread(new Transaction(account, "deposit", 500));
        Thread thread3 = new Thread(new Transaction(account, "withdraw", 300));
        Thread thread4 = new Thread(new Transaction(account, "deposit", 100));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final balance: $" + account.getBalance());
    }
}
