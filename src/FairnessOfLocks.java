import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyThread extends Thread {
    private final Lock lock;

    MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(java.time.LocalDateTime.now() + " - " + Thread.currentThread().getName() + " is locked");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
            System.out.println(java.time.LocalDateTime.now() + " - " + Thread.currentThread().getName() + " is unlocked");
        }
    }
}
public class FairnessOfLocks {

    public static void main(String[] args) {
        Lock sharedLock = new ReentrantLock(true);
        MyThread t1 = new MyThread("t0", sharedLock);
        MyThread t2 = new MyThread("t1", sharedLock);
        MyThread t3 = new MyThread("t2", sharedLock);


            t1.start();
            t2.start();
            t3.start();



    }
}
