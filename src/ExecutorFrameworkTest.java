import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorFrameworkTest {

    public static long factorial(int n) {
        if (n <= 1) {return n;}
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
            for (int i = 1; i < 10; i++) {
                int finalI = i;
                executorService.submit(() -> {
                    long result = factorial(finalI);
                    System.out.println(finalI + "! = " +result);
                });

            }
            //executorService.shutdown();
//            List<Runnable> runn = executorService.shutdownNow();
//            for (Runnable r : runn) {
//                r.run();
//            }
            executorService.shutdown();
            while(!executorService.awaitTermination(10, TimeUnit.MILLISECONDS)){
               // System.out.println("Waiting for threads to finish");
            };
        System.out.println("Time taken: " + (System.currentTimeMillis() - start));

        // executor submit() with callable. Future hold status and result of execution.
        executorService = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> 12;
        Future<?> future1 = executorService.submit(callable);
        if(future1.isDone()){
            System.out.println("Task completed");
        }
        System.out.println(future1.get());
        executorService.shutdown();
        System.out.println("-----------------------------------------------------------");;

        // executor submit() with runnable. Future holds only status of execution.
        executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            System.out.println("Runnable completed");
        };
        Future<?> future2 = executorService.submit(runnable);
        if(future1.isDone()){
            System.out.println("Task completed");
        }
        System.out.println(future2.get());
        executorService.shutdown();
        System.out.println("-----------------------------------------------------------");;

        // executor submit() with runnable and returns task result and status with future.
        executorService = Executors.newSingleThreadExecutor();
        Runnable runnable2 = () -> {
            System.out.println("Runnable completed");
        };
        Future<String> future3 = executorService.submit(runnable2, "success");
        System.out.println(future3.get());
        if(future3.isDone()){
            System.out.println("Task completed");
        }
        executorService.shutdown();
        System.out.println("-----------------------------------------------------------");;

        executorService = Executors.newFixedThreadPool(2);
        Callable<Integer> callable1 = () -> {
            Thread.sleep(1000);
            System.out.println("Callable1 completed");
            return 1;
        };
        Callable<Integer> callable2 = () -> {
            Thread.sleep(1000);
            System.out.println("Callable2 completed");
            return 2;
        };
        Callable<Integer> callable3 = () -> {
            Thread.sleep(1000);
            System.out.println("Callable3 completed");
            return 3;
        };
        List<Callable<Integer>> callables = Arrays.asList(callable1, callable2, callable3);
        List<Future<Integer>> futureCallables = executorService.invokeAll(callables,1,TimeUnit.SECONDS);
        for (Future<Integer> future : futureCallables) {
            System.out.println(future.isCancelled());
            try{ System.out.println(future.get());
            }
            catch (CancellationException e){
                System.out.println("Task cancelled");
            }
        }
        executorService.shutdown();
        Thread.sleep(10);
        System.out.println("Is executor for invokeAll() done - " + executorService.isTerminated());

        // invokeAll() is blocking
        System.out.println("Test");
        System.out.println("------------------------------------------------------------");;

        // invokeAny() returns result in case of callable instead of future.
        executorService = Executors.newFixedThreadPool(2);
        Integer i = executorService.invokeAny(callables);
        System.out.println(i);
        executorService.shutdown();

    }
}
