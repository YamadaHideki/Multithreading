import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
    public static int cores = Runtime.getRuntime().availableProcessors();
    static ExecutorService pool = Executors.newFixedThreadPool(cores);
}
