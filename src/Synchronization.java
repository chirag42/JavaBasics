import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter implements Runnable {
    private static int counter = 0;

    private static final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        boolean acquired = false;
        try {
            System.out.println(Thread.currentThread().getName() + " is here to take lock.");
            lock.lockInterruptibly();
            acquired = true;
            for (int i = 0; i < 1000; i++) {
//            if (Thread.currentThread().isInterrupted()) {
//                System.out.println(Thread.currentThread().getName() + " was interrupted");
//                break;
//            }
                Counter.increment();
                System.out.println(Thread.currentThread().getName() + " " + counter);
//                try {
//                    Thread.sleep(80);
//                } catch (InterruptedException e) {
//                    System.out.println(Thread.currentThread().getName() + " is interrupted");
//                    break;
//                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " is interrupted");
        }
        finally {
            if (acquired) {
                lock.unlock();
            }
        }

    }

    public static void increment(){
        counter++;
    }
    public int getCounter(){
        return counter;
    }
}

public class Synchronization {

    public static void main(String[] args) {
        try {
            Counter counter = new Counter();
            Thread t1 = new Thread(counter);
            Thread t2 = new Thread(counter);
            t1.start();
            t2.start();
            Thread.sleep(10);
            t2.interrupt();
            t1.join();
            t2.join();
            System.out.println(counter.getCounter());

        }
        catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " is interrupted");
        }

    }

}
