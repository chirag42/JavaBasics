import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final Lock lock = new ReentrantLock();
    private static int counter = 0;
    public void outerMethod() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            System.out.println("Outer Method");
            counter++;
            if (counter == 1000) {
                Thread.currentThread().interrupt();
            }
            innerMethod();
        }finally {
            lock.unlock();
        }

    }

    private void innerMethod() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            System.out.println("Inner Method");
            counter++;
            if (counter == 1000) {
                Thread.currentThread().interrupt();
            }
            outerMethod();
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantExample example = new ReentrantExample();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    example.outerMethod();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
