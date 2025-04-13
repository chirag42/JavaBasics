class B implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " CN was here");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
class A extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " World");
    }
}//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        a.start();
        B b = new B();
        Thread t1 = new Thread(b);
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
        Thread.sleep(100);
        System.out.println(t1.getState());
        t1.join();
        System.out.println(t1.getState());
        System.out.println(Thread.currentThread().getName() + " Hello");
    }
}