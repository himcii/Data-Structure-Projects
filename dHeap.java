/*
 * Name: Xiaotong Zuo
 * PID:  A15445495
 */

import java.util.*;

/**
 * Class of d-ary heaps
 * 
 * @param <T> Generic type
 */
public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {

    private T[] heap; // heap array
    private int d; // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // boolean to indicate whether heap is max or min

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        heap = (T[]) new Comparable[6];
        d = 2;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        heap = (T[]) new Comparable[heapSize];
        d = 2;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        if (d < 1) {throw new IllegalArgumentException();}
        heap = (T[]) new Comparable[heapSize];
        this.d = d;
        nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    /**
     * Returns the number of elements stored in the heap.
     *
     * @return number of elements in the heap
     */
    @Override
    public int size() {
        return nelems;
    }

    /**
     * Adds the given data to the heap.
     *
     * @param data to add
     * @throws NullPointerException if data is null
     */
    @Override
    public void add(T data) throws NullPointerException {
        if (data == null) {throw new NullPointerException();}
        if (nelems == heap.length) {resize();}
        heap[nelems] = data;
        bubbleUp(nelems);
        nelems++;
    }

    /**
     * Returns and removes the root element from the heap.
     *
     * @return the root element
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public T remove() throws NoSuchElementException {
        if (nelems == 0) {throw new NoSuchElementException();}
        T toReturn = heap[0];
        heap[0] = heap[nelems-1];
        nelems--;
        trickleDown(0);
        return toReturn;
    }

    /**
     * Clear all elements in the heap.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        nelems = 0;
    }

    /**
     * Returns the root element from the heap.
     *
     * @return the root element
     * @throws NoSuchElementException if the heap is empty
     */
    public T element() throws NoSuchElementException {
        if (nelems == 0) {throw new NoSuchElementException();}
        return heap[0];
    }

    /**
     * Helper method for trickling elements down.
     *
     * @param index to start
     */
    private void trickleDown(int index) {
        int childIndex = d * index + 1;
        T value = heap[index];

        while (childIndex < nelems) {
            // Find the max among the node and all the node's children
            T maxMinValue = value;
            int maxMinIndex = -1;
            for (int i = 0; i < d && i + childIndex < nelems; i++) {
                if ((isMaxHeap & heap[i + childIndex].compareTo(maxMinValue)>0)||
                        (!isMaxHeap & heap[i + childIndex].compareTo(maxMinValue)<0)) {
                    maxMinValue = heap[i + childIndex];
                    maxMinIndex = i + childIndex;
                }
            }

            if (maxMinValue == value) {
                return;
            }
            else {
                T temp = heap[index];
                heap[index] = heap[maxMinIndex];
                heap[maxMinIndex] = temp;
                index = maxMinIndex;
                childIndex = d * index + 1;
            }
        }
    }

    /**
     * Helper method for bubbling elements up.
     *
     * @param index to start
     */
    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = parent(index);
            if (heap[index].compareTo(heap[parentIndex])==0 ||
                    (heap[index].compareTo(heap[parentIndex])<0 == isMaxHeap))
                return;
            else {
                T temp = heap[index];
                heap[index] = heap[parentIndex];
                heap[parentIndex] = temp;
                index = parentIndex;
            }
        }
    }

    /**
     * Helper method that doubles the heap array when it is full.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int len = heap.length;
        T[] newArray = (T[]) new Comparable[len*2];
        System.arraycopy(heap, 0, newArray, 0, len);
        heap = newArray;
    }

    /**
     * Helper method for finding the index of parent
     *
     * @return the index
     */
    private int parent(int index) {
        return (index - 1) / d;
    }

}
