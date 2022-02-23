import java.lang.reflect.Array;
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

    public static int[] intArrayMulti(int count) throws InterruptedException, ExecutionException {
        int[] result = new int[10];

        List<Callable<int[]>> callableArrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        System.out.println("Start create array");
        int forCore = count / Pool.cores;

        for (int i = 0; i < Pool.cores; i++) {
            if (i == Pool.cores - 1) {
                callableArrayList.add(() -> {
                    int maxArr = forCore + (count - forCore * Pool.cores);
                    int[] res = new int[maxArr];
                    for (int k = 0; k < maxArr; k++) {
                        res[k] = (int) (Math.random() * Integer.MAX_VALUE);
                    }
                    return res;
                });
            } else {
                callableArrayList.add(() -> {
                    int[] res = new int[forCore];
                    for (int n = 0; n < forCore; n++) {
                        res[n] = (int) (Math.random() * Integer.MAX_VALUE);
                    }
                    return res;
                });
            }
        }
        List<Future<int[]>> futureResult = Pool.pool.invokeAll(callableArrayList);

        for (Future<int[]> fb : futureResult) {
            System.out.println(fb.get().length + ", 0: " + Arrays.stream(fb.get()).filter(a -> a == 0).sum());
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
                result, i -> (int) (Math.random() * Integer.MAX_VALUE)
        );

        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("End create array " + endTime + "ms");

        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] solo = intArray(299_999_999);
        System.out.println("0: " + Arrays.stream(solo).filter(a -> a == 0).count());
        solo = null;

        int[] lam = initIntArray(299_999_999);
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
