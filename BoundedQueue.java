
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> implements Queue<T> {

    private final LinkedList<T> items = new LinkedList<T>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be >0");
        }

        this.capacity = capacity;
    }

    @Override
    public void enq(T item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        lock.lock();
        try {
            while (items.size() == capacity) {
                notFull.await();
            }
            items.addLast(item);
            notEmpty.signal();  //wake one waiting consumer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("enq interrupted", e);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public T deq() {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            T item = items.removeFirst();

            notFull.signal();

            return item;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("deq interrupted", e);
        } finally {
            lock.unlock();
        }
    }

}

/**
 * One lock protects the whole queue but only one producer/consumer can operate at a time 
 * while loop around await() not if because conditions can wake in the wrong was and another thread may have grabbed the slot before 
 * signal() is enough in comparison to signalAll since enq only makes not empty trye and seq only makes not full true
 */