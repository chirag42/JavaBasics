public class MaxThreadTest {
    public static void main(String[] args) {
        int count = 0;
        try {
            while (true) {
                new Thread(() -> {
                    try { Thread.sleep(1000000); } catch (Exception ignored) {}
                }).start();
                count++;
            }
        } catch (Error e) {
            System.out.println("Max threads: " + count);
        }
    }
}
