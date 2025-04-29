import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private int counter = 0;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();


    public void incrementCounter() {
        writeLock.lock();
        try {
            counter += 1;

        }  finally {
            writeLock.unlock();
        }
    }

    public int getCounter() {
        readLock.lock();
        try{
            return counter;
        }finally {
            readLock.unlock();
        }
    }


    public static void main(String[] args) {
        ReadWriteLockExample counter = new ReadWriteLockExample();

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println(java.time.LocalDateTime.now() + " - " + Thread.currentThread().getName() + " reads counter with value = " + counter.getCounter());
                }
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    counter.incrementCounter();
                    System.out.println(java.time.LocalDateTime.now() + " - " + Thread.currentThread().getName() + " incremented the counter.");
                }
            }
        };

        try {
            Thread read = new Thread(readTask);
            Thread write = new Thread(writeTask);
            read.start();
            write.start();
            read.join();
            write.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
