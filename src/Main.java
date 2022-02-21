public class Main {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup mainGroup = new ThreadGroup("main group");
        final MyThread thread1 = new MyThread(mainGroup, "1-й поток");
        final MyThread thread2 = new MyThread(mainGroup, "2-й поток");
        final MyThread thread3 = new MyThread(mainGroup, "3-й поток");
        final MyThread thread4 = new MyThread(mainGroup, "4-й поток");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        Thread.sleep(15000);
        mainGroup.interrupt();
    }
}
