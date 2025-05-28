import java.util.concurrent.*;

public class FuturesTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(() -> {
            Thread.sleep(2000);
            System.out.println("Submit executed task");
           return 20;
        });
        Thread.sleep(1000);
        future.cancel(false);

        try {
            System.out.println(future.isCancelled());
            System.out.println(future.isDone());
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (CancellationException e){
            System.out.println("Future Cancellation - " + e.toString());
            //throw new RuntimeException(e);
        }finally {
            executorService.shutdown();
        }


    }
}
