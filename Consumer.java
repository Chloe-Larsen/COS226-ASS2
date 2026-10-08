public class Consumer implements Runnable {

    private final Queue<Integer> queue;

    public Consumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().threadId();

        for (int i = 0; i < Main.ITEMS_PER_CONSUMER; i++) {
            Integer item = queue.deq(); // can be null
            Main.log("Consumer " + threadId + " | Dequeued item: " + item);
        }
    }
}