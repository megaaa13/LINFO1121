package fundamentals;

import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty();

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor without argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private static class Node<E> {
        private final E item;
        private final Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    LinkedStack() {
       this.size = 0;
       top = null;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (this.empty()) {
            throw new EmptyStackException();
        }
        return top.item;
    }

    @Override
    public E pop() throws EmptyStackException {
        if (this.empty()) {
            throw new EmptyStackException();
        }
        E i = top.item;
        this.top = top.next;
        size--;
        return i;
    }

    @Override
    public void push(E item) {
        top = new Node<>(item, top);
        size++;
    }
}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack

    public ArrayStack() {
        array = (E[]) new Object[1];
    }


    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (this.empty())
            throw new EmptyStackException();
        return this.array[this.size - 1];
    }

    @Override
    public E pop() throws EmptyStackException {
        if (this.empty())
            throw new EmptyStackException();
        E item = this.array[this.size - 1];
        size--;
        if (this.array.length != 1 && size == this.array.length / 2) { // Optimize the memory to not keep a too large array
            E[] newArray = (E[]) new Object[size];
            System.arraycopy(this.array, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        return item;
    }

    @Override
    public void push(E item) {
        if (this.size == this.array.length) {
            E[] newArray = (E[]) new Object[size*2];
            System.arraycopy(this.array, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        this.array[size] = item;
        size++;
    }
}

