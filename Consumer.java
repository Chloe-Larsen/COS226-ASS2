import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final Queue<Job> queue;
    private final int itemsToConsume;
    private final AtomicInteger processedCount;

    public Consumer(Queue<Job> queue, int itemsToConsume, AtomicInteger processedCount) {
        this.queue = queue;
        this.itemsToConsume = itemsToConsume;
        this.processedCount = processedCount;
    }

    @Override
    public void run() {
        int consumed = 0;
        while (consumed < itemsToConsume) {
            Job job = queue.deq();

            if (job != null) {
                processedCount.incrementAndGet();
                consumed++;
            } else {                
                Thread.onSpinWait();
            }
        }
    }
}