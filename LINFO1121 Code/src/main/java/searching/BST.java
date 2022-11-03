package searching;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * With the given partial implementation of a Binary Search Tree, we ask you to implement
 * the following methods :
 * - minKey() which return the smallest key in the BST, or null if it is empty
 * - higherKey(Key key) which Returns the least key strictly greater than the given key,
 * or null if there is no such key
 * <p>
 * You also need to implement an iterator for the BST
 */
public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private Node root;             // root of BST

    private class Node {
        private final Key key;       // sorted by key
        private Value val;           // associated data
        private Node left, right;    // left and right subtrees
        private int size;            // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    /**
     * Search for key, update value if key is found. Grow table if key is new.
     *
     * @param key the key
     * @param val the value
     */
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * TODO
     * The expected complexity is O(h) where h is the height of the BST
     * <p>
     * Returns the minimum key
     *
     * @return the minimum key, null if the tree is empty
     */
    public Key minKey() {
        Node current = this.root;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;

    }


    /**
     * TODO
     * The expected complexity is O(h) where h is the height of the BST
     * <p>
     * Returns the least key strictly greater than the given key, or null if there is no such key.
     *
     * @param key - the key
     * @return the least key greater than key, or null if there is no such key
     */
    public Key higherKey(Key key) {
        return higherKey(root, key);
    }

    private Key higherKey(Node x, Key key) {
        // STUDENT return null;
        // BEGIN STRIP
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        // key >= x.key
        if (cmp >= 0) return higherKey(x.right, key);
        // key < x.key
        Key left = higherKey(x.left, key);
        if (left == null) return x.key;
        else return left;
        // END STRIP
    }


    /**
     * TODO
     * <p>
     * Returns an iterator that iterates through the keys in Incresing order
     * (In-Order transversal).
     *
     * @return an iterator that iterates through the items in FIFO order.
     */
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    // the iterator doesn't implement remove() optional method

    private class BSTIterator implements Iterator<Key> {

        // TODO feel free to add some state variables

        /*
         * TODO
         *
         * The expected complexity is O(h) where h is the height of the BST
         */
        private int size;
        private Stack<Node> s;

        public BSTIterator() {
            this.s = new Stack<>();
            Node current = root;
            while (current != null) {
                s.push(current);
                current = current.left;
            }
            this.size = size();
        }

        /**
         * TODO
         * <p>
         * The expected complexity is O(1)
         */
        public boolean hasNext() {
            if (this.size != size())
                throw new ConcurrentModificationException();
            return !this.s.isEmpty();
        }


        /*
         * TODO
         *
         * The expected complexity is O(h) where h is the height of the BST
         */
        public Key next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Node toOutput = s.pop();
            if (toOutput.right != null) {
                Node current = toOutput.right;
                while (current != null) {
                    s.push(current);
                    current = current.left;
                }
            }
            return toOutput.key;
        }

    }
}