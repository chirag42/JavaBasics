import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    static class User {
        String name;
        int id;
        public User(String name, int id) {
            this.name = name;
            this.id = id;
        }
        public String toString() { return name; }
    }

    static class Order{
        String item;
        Order(String item) {
            this.item = item;
        }
        public String toString() { return item; }
    }

    static List<Order> getOrdersForUser(User user) {
        sleep(2000);
        System.out.println("Fetched Orders");
        return Arrays.asList(new Order("Book"), new Order("Laptop"));
    }

    static User getUserById(int id) {
        sleep(1000);
        System.out.println("Fetched User");
        return new User("Chirag", 10);
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {

        }
    }

    static class Response {
        User user;
        List<Order> orders;
        Response(User user, List<Order> orders) {
            this.user = user;
            this.orders = orders;
        }
        public String toString() { return user + " => " + orders; }
    }

    public static void main(String[] args) {

        // We can also use custom executor service to create required number of threads and
        // manage instead of relying on CompletableFutures sunning on deamon threads.
        CompletableFuture<User> completableFutureUser = CompletableFuture.supplyAsync(() -> getUserById(10));
        CompletableFuture<List<Order>> completableFutureOrder = completableFutureUser.thenApplyAsync(user -> getOrdersForUser(user));

        CompletableFuture<Response> completableFutureResponse = completableFutureUser.thenCombine(completableFutureOrder, ((user, orders) -> new Response(user, orders)));
        completableFutureResponse.thenAccept(response -> {
            System.out.println("Final result = " + response);
        });
        System.out.println("Main thread doing other work...");

        // Optional: block at the end if needed to see output (for demo)
        completableFutureResponse.join();
        System.out.println("Main thread finished");
    }
}
