

class Mul1 extends Thread {
    Mul1(String name){
        super(name);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getPriority());
        for (int i = 0; i < 1000; i++) {
           System.out.println("3 x "+ i +"  = " + 3*i);
        }

    }
}

class Mul2 extends Thread {
    Mul2(String name){
        super(name);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getPriority());
        for (int i = 0; i < 1000; i++) {
           System.out.println("4 x " + i + "  = " + 4*i);
        }

    }
}

class Mul3 extends Thread {
    Mul3(String name){
        super(name);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getPriority());
        for (int i = 0; i < 1000; i++) {
          System.out.println("5 x " + i + "  = " + 5*i);
        }

    }
}

public class Multiply {
    public static void mul(int n){
        for (int i = 0; i < 1000; i++) {
          System.out.println(n + " x " +i+ "  = " + n*i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        mul(3);
        mul(4);
        mul(5);
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
        long timeTaken_NonMultithreaded = end - start;

        start = System.currentTimeMillis();
        Mul1 m1 = new Mul1("mul1");
        Mul2 m2 = new Mul2("mul2");
        Mul3 m3 = new Mul3("mul3");
        m1.start();
        m2.start();
        m3.start();
        m1.setPriority(Thread.MAX_PRIORITY);
        m3.setPriority(Thread.MIN_PRIORITY);
        m2.setPriority(Thread.NORM_PRIORITY);
        m1.join();
        m2.join();
        m3.join();
        end = System.currentTimeMillis();

        System.out.println("Time taken Multithreaded: " + (end - start) + " ms");
        System.out.println("Time taken Non-Multithreaded: " + (timeTaken_NonMultithreaded) + " ms");
    }
}
