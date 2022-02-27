import java.util.concurrent.RecursiveTask;

public class RecursiveTaskForArray extends RecursiveTask<Long> {
    private int start;
    private int end;
    private int[] array;
    private int threshold;    // percent 0-99

    public RecursiveTaskForArray(int start, int end, int[] array, int threshold) {
        this.start = start;
        this.end = end;
        this.array = array;
        this.threshold = threshold;
    }

    @Override
    protected Long compute() {
        if (end - start > (array.length / 100) * threshold) {
            return forkTasksAndGetResult();
        } else {
            long summ = 0;
            for (int i = start; i < end; i++) {
                summ += array[i];
            }
            return summ;
        }
    }

    private Long forkTasksAndGetResult() {
        final int middle = (end - start) / 2 + start;
        RecursiveTaskForArray task1 = new RecursiveTaskForArray(start, middle, array, threshold);
        RecursiveTaskForArray task2 = new RecursiveTaskForArray(middle, end, array, threshold);
        invokeAll(task1, task2);
        return task1.join() + task2.join();
    }
}
