/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static final boolean DEBUG_LOGS = false;
    public static final int TOTAL_JOBS = 12000;
    public static final int QUEUE_CAPACITY = 1000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("## 4 Producers & 4 Consumers\n| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |\n| -------- | ------- | -------- | ------- | -------- | ------- | ------- |");
        runExperiment("bounded", 4, 4);
        runExperiment("lockfree", 4, 4);
        System.out.println();

        System.out.println("---\n## 6 Producers & 2 Consumers\n| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |\n| -------- | ------- | -------- | ------- | -------- | ------- | ------- |");
        runExperiment("bounded", 6, 2);
        runExperiment("lockfree", 6, 2);
        System.out.println();

        System.out.println("---\n## 2 Producers & 6 Consumers\n| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |\n| -------- | ------- | -------- | ------- | -------- | ------- | ------- |");
        runExperiment("bounded", 2, 6);
        runExperiment("lockfree", 2, 6);
    }

    static void runExperiment(String queueType, int producerCount, int consumerCount) throws InterruptedException {
        if (TOTAL_JOBS % producerCount != 0 || TOTAL_JOBS % consumerCount != 0) {
            throw new IllegalArgumentException("TOTAL_JOBS must divide evenly");
        }

        int itemsPerProducer = TOTAL_JOBS / producerCount;
        int itemsPerConsumer = TOTAL_JOBS / consumerCount;

        Queue<Job> queue;
        if (queueType.equalsIgnoreCase("bounded")) {
            queue = new BoundedQueue<>(QUEUE_CAPACITY);
        } else {
            queue = new LockFreeQueue<>();
        }

        AtomicLong idGenerator = new AtomicLong(0);
        AtomicInteger producedCount = new AtomicInteger(0);
        AtomicInteger processedCount = new AtomicInteger(0);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < producerCount; ++i) {
            threads.add(new Thread(
                    new Producer(queue, itemsPerProducer, idGenerator, producedCount)));
        }

        for (int i = 0; i < consumerCount; ++i) {
            threads.add(new Thread(
                    new Consumer(queue, itemsPerConsumer, processedCount)));
        }

        long start = System.nanoTime();

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long elapsedNanos = System.nanoTime() - start;
        double seconds = elapsedNanos / 1_000_000_000.0;
        double throughput = TOTAL_JOBS / seconds;

        System.out.printf(
                "| %-8s | %d | %d | %.3fs | %.1f | %d | %d | %n",
                queueType, producerCount, consumerCount, seconds, throughput, producedCount.get(),
                processedCount.get());

        if (producedCount.get() != TOTAL_JOBS || processedCount.get() != TOTAL_JOBS) {
            throw new AssertionError("Produced/processed count mismatch!");
        }
    }

    public static void log(String message) {
        if (DEBUG_LOGS) {
            System.out.println(message);
        }
    }
}