package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        if (this.isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = last;
        } else {
            Node node = new Node();
            node.item = item;
            node.next = this.last.next;
            this.last.next = node;
            this.last = node;
        }
        this.n++;
        this.nOp++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.n--;
        this.nOp++;
        Node current = this.last;
        for (int i = 0; i < index; i++) { // We'll search for the one BEFORE the searched one, to close the circle after removing it.
            current = current.next;
        }
        Item removed = current.next.item;
        current.next = current.next.next;
        return removed;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator(this);
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        long nOpInit;
        int pos;
        Node current;
        CircularLinkedList<Item> list;

        private ListIterator(CircularLinkedList<Item> list) {
            this.list = list;
            if (!this.list.isEmpty()) {
                this.current = list.last.next;
            }
            this.pos = 0;
            this.nOpInit = list.nOp();
        }

        @Override
        public boolean hasNext() {
            this.IsConcurrentModification();
            return pos < list.size();
        }

        @Override
        public Item next() {
            this.IsConcurrentModification();
            Item i = current.item;
            if (this.hasNext()) {
                current = current.next;
                pos++;
            } else {
                throw new IndexOutOfBoundsException();
            }
            return i;
        }

        private void IsConcurrentModification() {
            if (this.nOpInit != this.list.nOp()) {
                throw new ConcurrentModificationException();
            }
        }
    }

}