/*
 * Name: Xiaotong Zuo
 * PID: A15445495
 */

import java.io.*;
import java.util.Stack;
import java.util.PriorityQueue;

/**
 * The Huffman Coding Tree
 */
public class HCTree {
    // alphabet size of extended ASCII
    private static final int NUM_CHARS = 256;
    // number of bits in a bytef
    private static final int BYTE_BITS = 8;

    // the root of HCTree
    private HCNode root;
    // the leaves of HCTree that contain all the symbols
    private HCNode[] leaves = new HCNode[NUM_CHARS];

    /**
     * The Huffman Coding Node
     */
    protected class HCNode implements Comparable<HCNode> {

        byte symbol; // the symbol contained in this HCNode
        int freq; // the frequency of this symbol
        HCNode c0, c1, parent; // c0 is the '0' child, c1 is the '1' child

        /**
         * Initialize a HCNode with given parameters
         *
         * @param symbol the symbol contained in this HCNode
         * @param freq   the frequency of this symbol
         */
        HCNode(byte symbol, int freq) {
            this.symbol = symbol;
            this.freq = freq;
        }

        /**
         * Getter for symbol
         *
         * @return the symbol contained in this HCNode
         */
        byte getSymbol() {
            return this.symbol;
        }

        /**
         * Setter for symbol
         *
         * @param symbol the given symbol
         */
        void setSymbol(byte symbol) {
            this.symbol = symbol;
        }

        /**
         * Getter for freq
         *
         * @return the frequency of this symbol
         */
        int getFreq() {
            return this.freq;
        }

        /**
         * Setter for freq
         *
         * @param freq the given frequency
         */
        void setFreq(int freq) {
            this.freq = freq;
        }

        /**
         * Getter for '0' child of this HCNode
         *
         * @return '0' child of this HCNode
         */
        HCNode getC0() {
            return c0;
        }

        /**
         * Setter for '0' child of this HCNode
         *
         * @param c0 the given '0' child HCNode
         */
        void setC0(HCNode c0) {
            this.c0 = c0;
        }

        /**
         * Getter for '1' child of this HCNode
         *
         * @return '1' child of this HCNode
         */
        HCNode getC1() {
            return c1;
        }

        /**
         * Setter for '1' child of this HCNode
         *
         * @param c1 the given '1' child HCNode
         */
        void setC1(HCNode c1) {
            this.c1 = c1;
        }

        /**
         * Getter for parent of this HCNode
         *
         * @return parent of this HCNode
         */
        HCNode getParent() {
            return parent;
        }

        /**
         * Setter for parent of this HCNode
         *
         * @param parent the given parent HCNode
         */
        void setParent(HCNode parent) {
            this.parent = parent;
        }

        /**
         * Check if the HCNode is leaf (has no children)
         *
         * @return if it's leaf, return true. Otherwise, return false.
         */
        boolean isLeaf() {
            return (getC0() == null)&(getC1() == null);
        }

        /**
         * String representation
         *
         * @return string representation
         */
        public String toString() {
            return "Symbol: " + this.symbol + "; Freq: " + this.freq;
        }

        /**
         * Compare two nodes
         *
         * @param o node to compare
         * @return int positive if this node is greater
         */
        public int compareTo(HCNode o) {
            if (freq < o.getFreq()) {return -1;}
            else if (freq == o.getFreq()) {
                if (symbol < o.getSymbol()) {return -1;}
            }
            return 1;
        }
    }

    /**
     * Returns the root node
     *
     * @return root node
     */
    public HCNode getRoot() {
        return root;
    }

    /**
     * Sets the root node
     *
     * @param root node to set
     */
    public void setRoot(HCNode root) {
        this.root = root;
    }

    /**
     * Build a HCTree based on the given frequency array
     *
     * @param freq list of frequencies of each symbol
     */
    public void buildTree(int[] freq) {
        PriorityQueue<HCNode> pQueue = new PriorityQueue<>();
        for (int i=0; i < freq.length; i++) {
            if (freq[i] != 0){
                leaves[i] = new HCNode((byte)i, freq[i]);
                pQueue.offer(leaves[i]);
            }
        }
        while (!(pQueue.size()==1)) {
            HCNode node0 = pQueue.poll();
            HCNode node1 = pQueue.poll();
            HCNode newNode = new HCNode(node1.getSymbol(),node0.getFreq()+node1.getFreq());
            newNode.setC0(node0);
            newNode.setC1(node1);
            node0.setParent(newNode);
            node1.setParent(newNode);
            pQueue.offer(newNode);
        }
        root = pQueue.peek();
    }

    /**
     * Encode the given symbol based on the HCTree and write the result to the
     * given stream
     *
     * @param symbol to encode
     * @param out output stream
     * @throws IOException
     */
    public void encode(byte symbol, BitOutputStream out) throws IOException {
        String bits = "";
        HCNode curNode = leaves[symbol & 0xff];
        HCNode parentNode;
        while (!(curNode == root)) {
            parentNode = curNode.getParent();
            if (parentNode.getC0() == curNode) {
                bits = 0 + bits;
            } else {bits = 1 + bits;}
            curNode = parentNode;
        }
        for (int i=0; i < bits.length(); i++) {
            out.writeBit(bits.charAt(i));
        }
    }

    /**
     * Decode the given stream and return the symbol
     *
     * @param in input stream
     * @return the symbol decoded
     * @throws IOException
     */
    public byte decode(BitInputStream in) throws IOException {
        HCNode curNode = root;
        while (!(curNode.isLeaf())) {
            if (in.readBit() == 0) {
                curNode = curNode.getC0();
            } else {
                curNode = curNode.getC1();
            }
        }
        return curNode.getSymbol();
    }

    /**
     * Encode the HCTree to the given stream
     *
     * @param node to start the traversal
     * @param out the output stream
     * @throws IOException
     */
    public void encodeHCTree(HCNode node, BitOutputStream out) throws IOException {
        if (!(node == null)) {
            if (node.isLeaf()) {
                out.writeBit(1);
                out.writeByte(node.getSymbol());
            }
            else {
                out.writeBit(0);
                encodeHCTree(node.getC0(),out);
                encodeHCTree(node.getC1(),out);
            }
        }
    }

    /**
     * Decode the HCTree from the input stream
     *
     * @param in input stream to decode
     * @return the root node
     * @throws IOException
     */
    public HCNode decodeHCTree(BitInputStream in) throws IOException {
        HCNode curNode = new HCNode((byte)in.readBit(),1);
        if (curNode.getSymbol()==0) {
            HCNode child0 = decodeHCTree(in);
            HCNode child1 = decodeHCTree(in);
            curNode.setC0(child0);
            curNode.setC1(child1);
            child0.setParent(curNode);
            child1.setParent(curNode);
        } else {
            curNode.setSymbol(in.readByte());
            leaves[curNode.getSymbol()] = curNode;
        }
        return curNode;
    }

}