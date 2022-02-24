import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static long[] newLongArray(int count) {

        long[] result = new long[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }

        return result;
    }

    public static long[] longArrayMulti(int count) throws InterruptedException, ExecutionException {

        long[] result = new long[count];
        List<Callable<Boolean>> callableArrayList = new ArrayList<>();
        int forCore = count / Pool.cores;

        for (int i = 0; i < Pool.cores; i++) {
            int finalI = i;
            if (i == Pool.cores - 1) {
                int start = forCore * i;
                callableArrayList.add(() -> {
                    for (int k = start; k <= count; k++) {
                        result[k] = (int) (Math.random() * Integer.MAX_VALUE);
                    }
                    System.out.println("i: " + finalI + ", start: " + start + ", end: " + count + ", thread: " + Thread.currentThread().getName());
                    return true;
                });
            } else {
                int start = (i == 0) ? 0 : forCore * i;
                int end = (i == 0) ? forCore : forCore * (i + 1);
                callableArrayList.add(() -> {
                    for (int n = start; n < end; n++) {
                        result[n] = (int) (Math.random() * Integer.MAX_VALUE);
                    }
                    System.out.println("i: " + finalI + ", start: " + start + ", end: " + result[(forCore * finalI) - 1] + ", thread: " + Thread.currentThread().getName());
                    return true;
                });
            }
        }
        List<Future<Boolean>> futureResult = Pool.pool.invokeAll(callableArrayList);
        for (Future<Boolean> fb : futureResult) {
            System.out.println(fb.get());
        }

        return result;
    }

    public static long[] newIntArrayParallel(int count) {
        long[] result = new long[count];

        Arrays.parallelSetAll(
                result, i -> (int) (Math.random() * Integer.MAX_VALUE)
        );

        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*MyTimer.start();
        newLongArray(499_999_999);
        MyTimer.end();*/

        MyTimer.start();
        long[] multiArray = longArrayMulti(499_999_999);
        System.out.println(Arrays.stream(multiArray).filter(x -> x == 0).count());
        System.out.println("test: " + multiArray[4583]);
        MyTimer.end();

        MyTimer.start();
        //newIntArrayParallel(299_999_999);
        MyTimer.end();

        /*long sumArray = 0;

        long startTime = System.nanoTime();
        System.out.println("Start");
        sumArray = Arrays.stream(intArray).sum();
        long endTime = (System.nanoTime() - startTime) / 1000_000;
        System.out.println("sum: " + sumArray + ", end time: " + endTime + "ms");*/

        Pool.pool.shutdown();
    }
}
