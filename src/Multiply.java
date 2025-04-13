

class Mul1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
           System.out.println("3 x "+ i +"  = " + 3*i);
        }

    }
}

class Mul2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
           System.out.println("4 x " + i + "  = " + 4*i);
        }

    }
}

class Mul3 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
          System.out.println("5 x " + i + "  = " + 5*i);
        }

    }
}

public class Multiply {
    public static void mul(int n){
        for (int i = 0; i < 100000; i++) {
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
        Mul1 m1 = new Mul1();
        Mul2 m2 = new Mul2();
        Mul3 m3 = new Mul3();
        m1.start();
        m2.start();
        m3.start();
        m1.join();
        m2.join();
        m3.join();
        end = System.currentTimeMillis();

        System.out.println("Time taken Multithreaded: " + (end - start) + " ms");
        System.out.println("Time taken Non-Multithreaded: " + (timeTaken_NonMultithreaded) + " ms");
    }
}
