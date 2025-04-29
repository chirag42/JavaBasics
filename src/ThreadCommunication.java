import java.sql.Time;
import java.util.Scanner;

class SharedResource{
    private int data;
    private int counter = 0;
    private boolean dataProduced;
    private final int totalConsumers;

    SharedResource(int totalConsumers){
        this.totalConsumers = totalConsumers;
    }

    public synchronized void produce(int data){
        while(dataProduced){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        dataProduced = true;
        counter = 0;
        this.data = data;
        System.out.println(Thread.currentThread().getName() + " Producing data: " + data);
        notifyAll();
    }

    public synchronized void consume(){
        while(!dataProduced || counter >= totalConsumers){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter++;
        System.out.println(Thread.currentThread().getName() + " consuming data: " + data);
        if(counter == totalConsumers){
            dataProduced = false;
        }
        notifyAll();
    }
}

class Producer implements Runnable{

    private final SharedResource sharedResource;

    public Producer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }
    @Override
    public void run() {
       for (int i = 0; i < 10000; i++) {
           sharedResource.produce(i);
       }
    }
}
class Consumer implements Runnable{

    private final SharedResource sharedResource;

    public Consumer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            sharedResource.consume();
        }
    }
}

public class ThreadCommunication {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int totalConsumers = scanner.nextInt();
        SharedResource sharedResource = new SharedResource(totalConsumers);
        Producer producer = new Producer(sharedResource);
        Consumer consumer = new Consumer(sharedResource);
        Thread producerThread = new Thread(producer, "Producer");
        long start = System.currentTimeMillis();
        Thread[] consumerThreads = new Thread[totalConsumers];
        for (int i = 0; i < totalConsumers; i++) {
            consumerThreads[i] = new Thread(consumer, "Consumer" + i);
            consumerThreads[i].start();
        }
        producerThread.start();
        producerThread.join();
        for (int i = 0; i < totalConsumers; i++) {
            consumerThreads[i].join();
        }
        System.out.println("Total time: " + (System.currentTimeMillis() - start));

    }
}
