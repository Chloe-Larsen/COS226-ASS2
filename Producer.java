import java.util.Random;

public class Producer implements Runnable {

    private final Queue<Integer> queue;
    private final Random random = new Random();

    public Producer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().threadId();

        for (int i = 0; i < Main.ITEMS_PER_PRODUCER; i++) {
            int item = 1 + random.nextInt(1000); // [1; 1000]
            Main.log("Producer " + threadId + " | Enqueuing item: " + item);
            queue.enq(item);
        }
    }
}