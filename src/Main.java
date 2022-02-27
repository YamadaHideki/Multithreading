import java.util.concurrent.*;

public class Main {

    public static int[] newIntArray(int count) {

        int[] result = new int[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }
        return result;
    }

    public static void main(String[] args) {

        MyTimer.start();
        System.out.println("Create array");
        int[] intArray = newIntArray(999_999_999);
        MyTimer.end();

        MyTimer.start();
        ForkJoinPool fp = ForkJoinPool.commonPool();
        System.out.println("ForkJoinPool sum: " + fp.invoke(new RecursiveTaskForArray(0, intArray.length, intArray, 10)));
        MyTimer.end();

        MyTimer.start();
        long summ = 0;
        for (int value : intArray) {
            summ += value;
        }
        System.out.println("One Thread summ: " + summ);
        MyTimer.end();
    }
}
