

class Pen{
    public synchronized void usePenWithPaper(Paper paper){
        System.out.println(Thread.currentThread().getName() + " is here to take pen and is waiting for paper.");
        paper.finishWriting();
    }

    public synchronized void finishWriting(){
        System.out.println(Thread.currentThread().getName() + " is here to take paper and finished writing.");
    }
}

class Paper{
    public synchronized void usePaperWithPen(Pen pen){
        System.out.println(Thread.currentThread().getName() + " is here to take paper and is waiting for pen.");
        pen.finishWriting();
    }

    public synchronized void finishWriting(){
        System.out.println(Thread.currentThread().getName() + " is here to take pen and finished writing.");
    }
}

class Task1 extends Thread{

    private final Paper paper;
    private final Pen pen;
    Task1(Paper paper, Pen pen){
        this.paper = paper;
        this.pen = pen;
    }

    @Override
    public void run() {
        paper.usePaperWithPen(pen);
    }
}

class Task2 extends Thread{
    private final Paper paper;
    private final Pen pen;
    Task2(Paper paper, Pen pen){
        this.paper = paper;
        this.pen = pen;
    }

    @Override
    public void run() {
        synchronized (paper) {
            pen.usePenWithPaper(paper);
        }
    }
}


public class Deadlock {
    public static void main(String[] args) throws InterruptedException {
        Paper paper = new Paper();
        Pen pen = new Pen();
        Task1 task1 = new Task1(paper, pen);
        Task2 task2 = new Task2(paper, pen);
        task1.start();
        task2.start();
        task1.join();
        task2.join();
        System.out.println(Thread.currentThread().getName() );
    }
}
