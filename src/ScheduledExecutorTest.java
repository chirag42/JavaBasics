import java.time.LocalTime;
import java.util.concurrent.*;

public class ScheduledExecutorTest {
    public static void main(String[] args) {
          //Task with fixed schedule but executes once.
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.schedule(() -> {
//            System.out.println("Executing scheduled task after 3 seconds");
//        }, 3, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();

        // Task with fixed schedule and rate of execution (executes after every x seconds) and terminates after some seconds.
//        ScheduledExecutorService scheduledExecutorServiceFix = Executors.newScheduledThreadPool(1);
//        scheduledExecutorServiceFix.scheduleAtFixedRate(() -> {
//            System.out.println("Executing scheduled task after every 3 seconds");
//        }, 3, 3, TimeUnit.SECONDS);
//
//        scheduledExecutorServiceFix.schedule(() -> {
//            System.out.println("Initiating Shutdown!!");
//            scheduledExecutorServiceFix.shutdown();
//        },18,TimeUnit.SECONDS);


        // Task with fixed schedule and delay of execution (executes after every x seconds of completion of previous task)
        // and terminates after some seconds.
        ScheduledExecutorService scheduledExecutorServiceDelay = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = scheduledExecutorServiceDelay.scheduleWithFixedDelay(() -> {
            System.out.println("Executing scheduled task after every 3 seconds");
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1, TimeUnit.SECONDS);


        scheduledExecutorServiceDelay.schedule(() -> {
            System.out.println("Initiating Shutdown!!");
            System.out.println(LocalTime.now());
            scheduledExecutorServiceDelay.shutdown();
            System.out.println(scheduledFuture.isDone());
        },18,TimeUnit.SECONDS);


    }
}
