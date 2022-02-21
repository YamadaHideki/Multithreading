public class MyThread extends Thread {

    public MyThread(ThreadGroup mainGroup, String s) {
        super(mainGroup, s);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println("Привет, я " + getName());
                Thread.sleep((int) (Math.random() * 2500) + 1500);
            }
        } catch (InterruptedException e) {

        }
    }
}
