import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccountReentrant {
    private static int balance = 100;

    private final Lock lock = new ReentrantLock();

    // explicit locks which give more control.
    public void withdraw(int amount) {
        try {
            System.out.println(Thread.currentThread().getName() + ": Trying to withdraw " + amount + " from bank");
            if (lock.tryLock(1006, TimeUnit.MILLISECONDS)) {
                try {
                    if (balance >= amount) {
                        System.out.println(Thread.currentThread().getName() + " : Withdrawing amount : " + amount);
                        //Thread.currentThread().interrupt();
                        Thread.sleep(500);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " : Withdrawal successful : " + amount);
                        System.out.println(Thread.currentThread().getName() + " : Remaining Balance : " + balance);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " : Not enough balance");
                    }
                }
                catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " : Interrupted");
                    Thread.currentThread().interrupt();
                }
                finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " : Could not acquire lock");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " : Interrupted");
            Thread.currentThread().interrupt();
        }
        if(Thread.currentThread().isInterrupted()){
            System.out.println(Thread.currentThread().getName() + " : Interrupted. Do some clean up work.");
            System.out.println(Thread.currentThread().getName() + " : Remaining Balance : " + balance);
        }
    }
}

class BankAccount {
    private static int balance = 100;

    // implicit locks which cause long waits for user threads.
    public synchronized void withdraw(int amount) throws InterruptedException {
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " : Withdrawing amount : " + amount);
            Thread.sleep(10000);
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " : Withdrawal successful : " + amount);
        } else {
            System.out.println(Thread.currentThread().getName() + " : Not enough balance");
        }
    }

}

public class TestLocks {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    account.withdraw(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
//       Thread t1 = new Thread(runnable);
//       Thread t2 = new Thread(runnable);
//       t1.start();
//       t2.start();

        BankAccountReentrant account1 = new BankAccountReentrant();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                account1.withdraw(50);

            }
        };
        Thread t3 = new Thread(runnable1);
        Thread t4 = new Thread(runnable1);
        Thread t5 = new Thread(runnable1);
        t3.start();
//        try {
//            Thread.sleep(1000); // Let it start first
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t3.interrupt();
        t4.start();
        t5.start();
        t3.join();
        t4.join();
        t5.join();
    }

}


