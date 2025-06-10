import java.util.concurrent.atomic.AtomicInteger;

class CounterExample {

    // Volatile only makes sure that threads read from main memory instead of thread's local copy of variable.
    // In VolatileExample, It is fine even if multiple threads set flag to true and reader reads post that.
    // But In case of counter, if multiple threads use same copy of counter from counter Obj in memory,
    // It will result in single increment and incorrect final result.
    // To overcome this, we can use a thread-safe option such as Atomic Integer
    //private volatile int counter = 0;
    private AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        //counter++;
        counter.incrementAndGet();
    }
    public int getCounter() {
        return counter.get();
    }

}

public class AtomacityExample {
    public static void main(String[] args) throws InterruptedException {
        CounterExample counterExample = new CounterExample();
        Thread writerThread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counterExample.increment();
            }
        });
        Thread writerThread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counterExample.increment();
            }
        });
        writerThread1.start();
        writerThread2.start();
        writerThread1.join();
        writerThread2.join();
        System.out.println("Counter final value = " + counterExample.getCounter());
    }
}
