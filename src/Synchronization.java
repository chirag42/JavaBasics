class Counter extends Thread {
    private static int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Counter.increment();
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

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter);
        t1.start();
        Thread t2 = new Thread(counter);
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter.getCounter());

    }

}
