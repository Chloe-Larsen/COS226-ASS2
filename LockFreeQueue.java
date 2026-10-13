/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/

import java.util.concurrent.atomic.AtomicReference;
public class LockFreeQueue<T> implements Queue<T> {

    private static class Node<T> {
        final T value;
        final AtomicReference<Node<T>> next = new AtomicReference<>(null);

        Node(T value) {
            this.value = value;
        }
    }

    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    public LockFreeQueue() {
        Node<T> sentinel = new Node<>(null);
        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
    }

    @Override
    public void enq(T item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }
        Node<T> newNode = new Node<>(item);

        while (true) {
            Node<T> last = tail.get();
            Node<T> next = last.next.get();

            if (last == tail.get()) {
                if (next == null) {
                    if (last.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(last, newNode); 
                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    @Override
    public T deq() {
        while (true) {
            Node<T> first = head.get();
            Node<T> last = tail.get();
            Node<T> next = first.next.get();

            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        return null; 
                    }
                    tail.compareAndSet(last, next);
                } else {
                    T value = next.value;
                    if (head.compareAndSet(first, next)) {
                        return value;
                    }
                }
            }
        }
    }
}
 