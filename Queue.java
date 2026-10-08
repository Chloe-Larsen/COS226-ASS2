
public interface Queue<T> {

    void enq(T item); 
    //insert into queue
    //Bounded: blocks if queue is full 
    //LockFree: never blocks

    void deq();
    //remove and return next item 
    //Bounded: blocks if queue is empty 
    //LockFree: returns null if queue empty
}
