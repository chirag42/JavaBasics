import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final Lock lock = new ReentrantLock();
    private static int counter = 0;
    public void outerMethod() throws InterruptedException {
        if (counter > 1000) return;
        lock.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName() + " calling Outer Method");
            counter++;
            Thread.sleep(5000);
            innerMethod();
        }finally {
            lock.unlock();
        }

    }

    private void innerMethod() throws InterruptedException {
        if (counter > 1000) return;
        lock.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName() + " calling Inner Method");
            counter++;
            Thread.sleep(5000);
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
                    System.out.println(Thread.currentThread().getName() + " is interrupted");

                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t2.start();
        Thread.sleep(200);
        t1.start();
        Thread.sleep(200);
        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();
    }
}
