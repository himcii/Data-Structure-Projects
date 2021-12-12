/*
 * NAME: Xiaotong Zuo
 * PID: A15445495
 */

/**
 * Class of doubly linked stacks
 * @param <T> generic container
 * @author Xiaotong Zuo
 * @since 2021/2/3
 */
public class DLLStack<T> {

    private DoublyLinkedList<T> stack;

    /**
     * Constructor to create a DLLStack. Initialize the stack to be a
     * doubly linked list
     */
    public DLLStack() {
        this.stack = new DoublyLinkedList<T>();
    }

    /**
     * Get the size of the stack
     * @return size of the stack
     */
    public int size() {
        return this.stack.size();
    }

    /**
     * Check if the stack is empty
     * @return whether the stack is empty
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * Push the data into the stack
     * @param data the data to add
     */
    public void push(T data) {
        if (data == null) {throw new IllegalArgumentException();}
        this.stack.add(0, data);
    }

    /**
     * Pop a data from the stack
     * @return the data popped
     */
    public T pop() {
        if (this.stack.isEmpty()) {return null;}
        return this.stack.remove(0);
    }

    /**
     * Peek the data at the top of the stack
     * @return the data at the top of the stack
     */
    public T peek() {
        if (this.stack.isEmpty()) {return null;}
        return this.stack.get(0);
    }

}
