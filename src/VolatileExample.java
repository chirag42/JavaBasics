class SharedObject {
    private volatile boolean flag = false;
    public boolean getFlagIfTrue() {
        while(!flag) {
            // nothing
        }
        return flag;
    }

    public void setFlag(boolean flag) {
        System.out.println("WriterThread has set flag to true ");
        this.flag = flag;
    }
}

public class VolatileExample {
    public static void main(String[] args) {
        SharedObject sharedObject = new SharedObject();
        Thread writingThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ignored) {

            }
            sharedObject.setFlag(true);
        });
        Thread readingThread = new Thread(() -> {
            boolean flagval = sharedObject.getFlagIfTrue();
            System.out.println("Flag set to " + flagval + " by writing thread");
        });
        writingThread.start();
        readingThread.start();

    }
}
