/*
 * Name: Xiaotong Zuo
 * PID:  A15445495
 */

import java.util.*;

/**
 * Binary search tree implementation.
 * 
 * @author Xiaotong Zuo
 * @since  2021/2/16
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = dataList;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = new LinkedList<>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            return left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            return right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            return dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setLeft(BSTNode newleft) {
            left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setRight(BSTNode newright) {
            right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            return dataList.remove(data);
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        root = null;
        nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return nelems;
    }

    /**
     * Insert a key into BST
     * 
     * @param key To be inserted
     * @return true if insertion is successful and false otherwise
     * @throws NullPointerException if key is null
     */
    public boolean insert(T key) {
        if (key == null) {throw new NullPointerException();}
        BSTNode node = new BSTNode(null, null, key);
        if (root == null) {
            root = node;
            nelems++;
            return true;
        }
        else {
            BSTNode currentNode = root;
            while (true) {
                if (node.key.compareTo(currentNode.key)<0) {
                    if (currentNode.left == null) {
                        currentNode.left = node;
                        nelems++;
                        return true;
                    } else {currentNode = currentNode.left;}
                } else if (node.key.compareTo(currentNode.key)>0) {
                    if (currentNode.right == null) {
                        currentNode.right = node;
                        nelems++;
                        return true;
                    } else {currentNode = currentNode.right;}
                } else {return false;}
            }
        }
    }

    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        if (key == null) {throw new NullPointerException();}
        BSTNode currentNode = root;
        while (!(currentNode == null)) {
            if (key.compareTo(currentNode.key)==0) {return true;}
            else if (key.compareTo(currentNode.key)<0) {
                currentNode = currentNode.left;
            }
            else {currentNode = currentNode.right;}
        }
        return false;
    }

    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If either key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        if (key == null || data == null) {throw new NullPointerException();}
        if (!(findKey(key))) {throw new IllegalArgumentException();}
        BSTNode currentNode = root;
        while (true) {
            if (key.compareTo(currentNode.key)==0) {
                currentNode.dataList.add(data);
                break;
            }
            else if (key.compareTo(currentNode.key)<0) {
                currentNode = currentNode.left;
            }
            else {currentNode = currentNode.right;}
        }
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        if (key == null) {throw new NullPointerException();}
        if (!(findKey(key))) {throw new IllegalArgumentException();}
        BSTNode currentNode = root;
        while (true) {
            if (key.compareTo(currentNode.key)==0) {
                return currentNode.dataList;
            }
            else if (key.compareTo(currentNode.key)<0) {
                currentNode = currentNode.left;
            }
            else {currentNode = currentNode.right;}
        }
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        return findHeightHelper(root);
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {
        if (root == null) {return -1;}
        else {
            int leftHeight = findHeightHelper(root.getLeft());
            int rightHeight = findHeightHelper(root.getRight());
            int maxSubHeight = Math.max(leftHeight, rightHeight);
            return 1 + maxSubHeight;
        }
    }

    /* * * * * BST Iterator * * * * */

    public class BSTree_Iterator implements Iterator<T> {
        Stack<BSTNode> stack;

        public BSTree_Iterator() {
            stack = new Stack<>();
            BSTNode curNode = root;
            while (!(curNode == null)) {
                stack.push(curNode);
                curNode = curNode.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            if (!(hasNext())) {throw new NoSuchElementException();}
            BSTNode returnedNode = stack.pop();
            BSTNode curNode = returnedNode.right;
            if (!(curNode == null)) {
                while (!(curNode.left == null)) {
                    stack.push(curNode);
                    curNode = curNode.left;
                }
            }
            return returnedNode.key;
        }
    }

    public Iterator<T> iterator() {
        return new BSTree_Iterator();
    }

    /* * * * * Extra Credit Methods * * * * */

    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
        /* TODO */
        return null;
    }

    public int levelCount(int level) {
        /* TODO */
        return -1;
    }
}
