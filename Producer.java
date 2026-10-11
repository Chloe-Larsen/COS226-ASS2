import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Producer implements Runnable {

    private final Queue<Job> queue;
    private final int itemsToProduce;
    private final AtomicLong idGenerator;
    private final AtomicInteger producedCount;

    public Producer(Queue<Job> queue, int itemsToProduce, AtomicLong idGenerator, AtomicInteger producedCount) {
        this.queue = queue;
        this.itemsToProduce = itemsToProduce;
        this.idGenerator = idGenerator;
        this.producedCount = producedCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            Job job = new Job(idGenerator.incrementAndGet());
            queue.enq(job);
            producedCount.incrementAndGet();
        }
    }
}