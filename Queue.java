/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/
public interface Queue<T> {

    void enq(T item); 
    //insert into queue
    //Bounded: blocks if queue is full 
    //LockFree: never blocks

    T deq();
    //remove and return next item 
    //Bounded: blocks if queue is empty 
    //LockFree: returns null if queue empty
}
