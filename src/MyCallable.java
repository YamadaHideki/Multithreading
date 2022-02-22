import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    int countMessages = 0;

    @Override
    public Integer call() throws Exception {
        int random = (int) ((Math.random() * 10) + 1);
        for (int i = 0; i < random; i++, ++countMessages) {
            System.out.println("Привет, я " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
        System.out.println(Thread.currentThread().getName() + ", messages: " + countMessages);
        return countMessages;
    }
}