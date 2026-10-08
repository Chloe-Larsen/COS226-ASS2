public class Main {

    public static boolean DEBUG_LOGS = true; // disable for performance testing
    public static final int CONSUMER_COUNT = 2;
    public static final int ITEMS_PER_CONSUMER = 100;
    public static final int PRODUCER_COUNT = 2;
    public static final int ITEMS_PER_PRODUCER = 100;

    public static void main(String[] args) {

        /* ========== EXAMPLE CREATING PRODUCER & CONSUMER THREADS ========== */

        Queue<Integer> queue = new LockFreeQueue<>();

        Consumer consumer = new Consumer(queue);
        Thread consumerThread1 = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer);

        Producer producer = new Producer(queue);
        Thread producerThread1 = new Thread(producer);
        Thread producerThread2 = new Thread(producer);

        /* ========== EXAMPLE END ========== */
    }

    public static void log(String message) {
        if (DEBUG_LOGS) {
            System.out.println(message);
        }
    }
}