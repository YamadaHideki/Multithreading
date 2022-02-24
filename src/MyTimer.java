public class MyTimer {
    private static long start = 0;

    public static void start() {
        start = System.nanoTime();
    }

    public static void end() {
        long end = (System.nanoTime() - start) / 1000_000;
        System.out.println("finished: " + end + "ms");
    }


}
