public class WithoutExecutorFrameworkTest {
    public static long factorial(int n) {
        System.out.println("Running " + n + "! on " + Thread.currentThread().getName());
        if (n <= 1) {return n;}
        long result = 1;
        for (int i = 1; i <= n; i++) {
           result *= i;
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        //Without thread pool
        //The code below is blocking main thread as it computes each factorial.
        System.out.println("--------------------------------Without Thread Pool----------------------------------");
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < 20; i++) {
            long factorial = factorial(i);
            System.out.println(i + "! : " + factorial);
        }
        System.out.println("Time taken: " + (System.currentTimeMillis() - startTime));


        //With Thread pool.
        //The code below with threads is non-blocking (main thread starts and with each thread, lets them run in parallel and computes factorial).
        System.out.println("--------------------------------With Thread Pool, Thread for each task----------------------------------");
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[19];
        for (int i = 1; i < 20; i++) {
            int finalI = i;
            threads[i-1] = new Thread(() -> {
                long result = factorial(finalI);
                System.out.println(finalI + "! : " + result);
            });
            threads[i-1].start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Time Taken = " + (System.currentTimeMillis() - start));

        // With fixed threads with intention of reusability but, potentially starting a new thread before the previous one finishes,
        // and overwriting its reference in array.
        System.out.println("--------------------------------With Thread Pool, but same as above just reusing array index----------------------------------");
        start = System.currentTimeMillis();
        Thread[] threadsFixed = new Thread[2];
        for (int i = 1; i < 20; i++) {
            int finalI = i;
            threadsFixed[i % 2] = new Thread(() -> {
                long result = factorial(finalI);
                System.out.println(finalI + "! : " + result);
            });
            threadsFixed[i%2].start();
        }
        for (Thread t : threadsFixed) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Time Taken = " + (System.currentTimeMillis() - start));

    }
}

