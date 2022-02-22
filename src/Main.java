import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static List<MyCallable> newCallableObjects() {
        List<MyCallable> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(new MyCallable());
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(4);

        int countMessages = pool.invokeAny(newCallableObjects());
        System.out.println("First finished thread: " + countMessages);

        int allMesages = pool.invokeAll(newCallableObjects()).stream()
                .mapToInt(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException ignored) { }
                    return 0;
                })
                .sum();
        System.out.println("All messages: " + allMesages);

        pool.shutdown();
    }
}
