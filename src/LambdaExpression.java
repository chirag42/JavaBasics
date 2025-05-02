interface Add {
    // using lambda for interface with single abstract method.
    int add(int x, int y);
}

public class LambdaExpression {

    public static void main(String[] args) throws InterruptedException {

        Add addImpl = (x,y) -> x + y;

        System.out.println(addImpl.add(1,2));

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        t1.start();
        Thread.sleep(100);
        t1.setName("Thread-1");

    }
}
