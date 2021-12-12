/*
 * NAME: Xiaotong Zuo
 * PID: A15445495
 */

import java.util.AbstractList;

/**
 * Class of doubly linked list, extends AbstrsctList class.
 * @author Xiaotong Zuo
 * @since 2021/2/3
 */
public class DoublyLinkedList<T> extends AbstractList<T> {

    private int nelems;
    private Node head;
    private Node tail;

    /**
     * Node for chaining together to create a linked list
     */
    protected class Node {

        T data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         */
        private Node(T element) {
            this.data = element;
        }

        /**
         * Constructor to create singleton link it between previous and next
         *
         * @param element  Element to add, can be null
         * @param nextNode successor Node, can be null
         * @param prevNode predecessor Node, can be null
         */
        private Node(T element, Node nextNode, Node prevNode) {
            this.data = element;
            this.next = nextNode;
            this.prev = prevNode;
        }

        /**
         * Set the element
         *
         * @param element new element
         */
        public void setElement(T element) {
            this.data = element;
        }

        /**
         * Accessor to get the Nodes Element
         */
        public T getElement() {
            return this.data;
        }

        /**
         * Set the next node in the list
         *
         * @param n new next node
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Get the next node in the list
         *
         * @return the successor node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Set the previous node in the list
         *
         * @param p new previous node
         */
        public void setPrev(Node p) {
            this.prev = p;
        }


        /**
         * Accessor to get the prev Node in the list
         *
         * @return predecessor node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Remove this node from the list.
         * Update previous and next nodes
         */
        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
    }

    /**
     * Creates a new, empty doubly-linked list.
     */
    public DoublyLinkedList() {
        this.nelems = 0;
        Node dummy1 = new Node(null);
        Node dummy2 = new Node(null);
        this.head = dummy1;
        this.tail = dummy2;
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    /**
     * Add an element to the end of the list
     *
     * @param element data to be added
     * @return whether or not the element was added
     * @throws NullPointerException if data received is null
     */
    @Override
    public boolean add(T element) throws NullPointerException {
        if (element == null) {throw new NullPointerException();}
        Node newNode = new Node(element, this.tail, this.tail.prev);
        this.tail.prev.next = newNode;
        this.tail.prev = newNode;
        this.nelems += 1;
        return true;
    }


    /**
     * Adds an element to a certain index in the list, shifting exist elements
     * create room. Does not accept null values.
     *
     * @param index An integer
     * @param element data to be added
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws NullPointerException if the element is null
     */
    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {
        if (index < 0 || index > this.nelems) {throw new IndexOutOfBoundsException();}
        if (element == null) {throw new NullPointerException();}
        if (index == this.nelems) {
            this.add(element);
        } else {
            Node temp = this.head;
            for (int i=0;i < index;i++) {
                temp = temp.next;
            }
            Node newNode = new Node(element, temp.next, temp);
            temp.next.prev = newNode;
            temp.next = newNode;
            this.nelems += 1;
        }
    }

    /**
     * Clear the linked list
     */
    @Override
    public void clear() {
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.nelems = 0;
    }

    /**
     * Determine if the list contains the data element anywhere in the list.
     *
     * @param element to be checked
     * @return whether the element is found
     */
    @Override
    public boolean contains(Object element) {
        T data = (T)element;
        Node temp = this.head.next;
        while (temp.data != null) {
            if (temp.data == element) {return true;}
            temp = temp.next;
        }
        return false;
    }

    /**
     * Retrieves the element stored with a given index on the list.
     *
     * @param index An integer
     * @return the element stored
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.nelems) {throw new IndexOutOfBoundsException();}
        Node temp = this.head.next;
        for (int i = 0; i < index;i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /**
     * Helper method to get the Nth node in our list
     *
     * @param index An integer
     * @return the node at the index
     */
    private Node getNth(int index) {
        Node temp = this.head.next;
        for (int i = 0; i < index;i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * Determine if the list empty
     *
     * @return whether the list is empty
     */
    @Override
    public boolean isEmpty() {
        return this.nelems == 0;
    }

    /**
     * Remove the element from position index in the list
     *
     * @param index An integer
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.nelems) {throw new IndexOutOfBoundsException();}
        Node nodeToRemove = this.getNth(index);
        Node sucNode = nodeToRemove.next;
        Node predNode = nodeToRemove.prev;
        sucNode.prev = predNode;
        predNode.next = sucNode;
        this.nelems -= 1;
        return nodeToRemove.data;
    }

    /**
     * Set the value of an element at a certain index in the list.
     *
     * @param index An integer
     * @param element the data to set
     * @return the original data
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws NullPointerException if the new element is null
     */
    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {
        if (index < 0 || index >= this.nelems) {throw new IndexOutOfBoundsException();}
        if (element == null) {throw new NullPointerException();}
        Node nodeToSet = this.getNth(index);
        T originalData = nodeToSet.data;
        nodeToSet.data = element;
        return originalData;
    }

    /**
     * Retrieves the amount of elements that are currently on the list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return this.nelems;
    }

    /**
     * String representation of this list in the form of:
     * "[(head) -> elem1 -> elem2 -> ... -> elemN -> (tail)]"
     *
     * @return the string representation of the list
     */
    @Override
    public String toString() {
        String result = "[(head) -> ";
        int i = 0;
        Node temp = this.head;
        while (i < this.nelems) {
            temp = temp.next;
            result = result + temp.data.toString() + " -> ";
            i++;
        }
        result += "(tail)]";
        return result;
    }

    /* ==================== EXTRA CREDIT ==================== */

    /**
     * Inserts another linked list of the same type into this one
     *
     * @param index An integer
     * @param otherList the list to be inserted
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void splice(int index, DoublyLinkedList<T> otherList) throws IndexOutOfBoundsException {
        if (index < 0 || index > this.nelems) throw new IndexOutOfBoundsException();
        if (!otherList.isEmpty()) {
            this.nelems += otherList.size();
            Node following = this.getNth(index);
            following.prev.next = otherList.head.next;
            otherList.head.next.prev = following.prev;
            otherList.tail.prev.next = following;
            following.prev = otherList.tail.prev;
        }
    }

    /**
     * Determine the starting indices that match the subSequence
     *
     * @param subsequence the list to be check
     * @return an array of all indices found
     */
    public int[] match(DoublyLinkedList<T> subsequence) {

        DoublyLinkedList<Integer> indices = new DoublyLinkedList<>();

        for (int i=0;i<this.nelems-subsequence.size()+1;i++) {
            boolean overlap = true;
            for (int j=0;j<subsequence.size();j++) {
                overlap = this.get(i+j) == subsequence.get(j);
                if (!overlap) break;
            }
            if (overlap) {
                indices.add(i);
            }
        }

        int[] startingIndices = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            startingIndices[i] = indices.get(i);
        }
        return startingIndices;
    }

}