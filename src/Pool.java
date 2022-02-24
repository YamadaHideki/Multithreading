import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
    public static int cores = Runtime.getRuntime().availableProcessors();
    public static ExecutorService pool = Executors.newFixedThreadPool(cores);
}
