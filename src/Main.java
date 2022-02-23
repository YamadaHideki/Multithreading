import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static int[] intArray(int count) {
        long startTime = System.nanoTime();
        System.out.println("Start create array");

        int[] result = new int[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }
        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("End create array " + endTime + "ms");
        return result;
    }

    public static int[] intArrayMulti(int count) throws InterruptedException {
        int[] result = new int[count];

        List<Callable<Boolean>> callableArrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        System.out.println("Start create array");
        int forCore = count / Pool.cores;

        for (int i = 0, j; i < Pool.cores; i++) {
            int start = (i <= 0) ? 0 : i * forCore;
            if (i == Pool.cores - 1) {
                callableArrayList.add(() -> {
                    for (int k = start; k < forCore + (count - forCore * Pool.cores); k++) {
                        result[k] = (int) (Math.random() * Integer.MAX_VALUE) + 1;
                    }
                    return true;
                });
            } else {
                callableArrayList.add(() -> {
                    for (int n = start; n < forCore + start; n++) {
                        result[n] = (int) (Math.random() * Integer.MAX_VALUE) + 1;
                    }
                    return true;
                });
            }
        }
        List<Future<Boolean>> futureResult = Pool.pool.invokeAll(callableArrayList);

        for (Future<Boolean> fb : futureResult) {
            System.out.println(fb);
        }
        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("End create array " + endTime + "ms");
        return result;
    }

    public static int[] initIntArray(int count) {
        int[] result = new int[count];

        long startTime = System.nanoTime();
        System.out.println("Start create array");

        Arrays.parallelSetAll(
                result, i -> new Random().nextInt()
        );

        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("End create array " + endTime + "ms");

        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] solo = intArray(999_999_999);
        System.out.println("0: " + Arrays.stream(solo).filter(a -> a == 0).count());
        solo = null;
        int[] lam = initIntArray(999_999_999);
        System.out.println("0: " + Arrays.stream(lam).filter(a -> a == 0).count());
        lam = null;
        //pool.shutdown();


        /*long[] intArray = intArray(999_999_999);
        long sumArray = 0;

        long startTime = System.nanoTime();
        System.out.println("Start");
        sumArray = Arrays.stream(intArray).sum();
        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("sum: " + sumArray + ", end time: " + endTime + "ms");*/
    }
}
