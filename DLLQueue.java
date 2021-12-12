/*
 * NAME: Xiaotong Zuo
 * PID: A15445495
 */

/**
 * Class of doubly linked queues
 * @param <T> generic container
 * @author Xiaotong Zuo
 * @since 2021/2/3
 */
public class DLLQueue<T> {

    private DoublyLinkedList<T> queue;

    /**
     * Constructor to create a DLLQueue. Initialize the queue to be a
     * doubly linked list
     */
    public DLLQueue() {
        this.queue = new DoublyLinkedList<T>();
    }

    /**
     * Get the size of the queue
     * @return size of the queue
     */
    public int size() {
        return this.queue.size();
    }

    /**
     * Check if the queue is empty
     * @return whether the queue is empty
     */
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * Enqueue the data into the queue
     * @param data the data to add
     */
    public void enqueue(T data) {
        if (data == null) {throw new IllegalArgumentException();}
        this.queue.add(data);
    }

    /**
     * Dequeue a data from the queue
     * @return the data dequeued
     */
    public T dequeue() {
        if (this.queue.isEmpty()) {return null;}
        return this.queue.remove(0);
    }

    /**
     * Peek the data at the front of the queue
     * @return the data at the front of the queue
     */
    public T peek() {
        if (this.queue.isEmpty()) {return null;}
        return this.queue.get(0);
    }

}
