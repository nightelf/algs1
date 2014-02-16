package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    /**
     * Size of the deque.
     */
    private static final int CAPACITY_START = 4;

    /**
     * deque items.
     */
    private Item[] s;

    /**
     * Size of the deque.
     */
    private int N = 0;

    /**
     * index of the queue head.
     */
    private int head = 0;

    /**
     * index of the queue tail.
     */
    private int tail = 0;

    /**
     * Construct an empty deque.
     */
    public Deque() {

        s = (Item[]) new Object[CAPACITY_START];
    }

    /**
     * Is the deque empty?
     * @return boolean
     */
    public boolean isEmpty() {

        return N == 0;
    }

    /**
     * Return the number of items on the deque.
     * @return
     */
    public int size() {

        return N;
    }

    /**
     * insert the item at the head.
     * @param item the item.
     */
    public void addFirst(Item item) {

        if (null == item) {
            throw new NullPointerException();
        }
        conditionalGrow();
        int newHead = getIncrementIndex(head);
        s[head] = item;
        head = newHead;
        N++;
    }

    /**
     * insert the item at the tail.
     * @param item the item.
     */
    public void addLast(Item item) {

        if (null == item) {
            throw new NullPointerException();
        }
        conditionalGrow();
        tail = getDecrementIndex(tail);
        s[tail] = item;
        N++;
    }

    /**
     * delete and return the item at the head.
     * 
     * @return
     */
    public Item removeFirst() {

        if (0 == N) {
            throw new NoSuchElementException ();
        }
        conditionalShrink();
        head = getDecrementIndex(head);
        N--;
        return s[head];
    }

    /**
     * delete and return the item at the tail.
     * 
     * @return
     */
    public Item removeLast() {

        if (0 == N) {
            throw new NoSuchElementException ();
        }
        conditionalShrink();
        int oldTail = getIncrementIndex(tail);
        tail = getIncrementIndex(tail);
        N--;
        return s[oldTail];
    }

    /**
     * return an iterator over items in order from head to end.
     */
    public Iterator<Item> iterator() {
        
        return new DequeIterator();
    }

    /**
     * Conditionally grow the array if size is at capacity.
     */ 
    private void conditionalGrow() {

        if (N == s.length) {
            resize(2 * s.length);
        }
    }

    /**
     * Conditionally shrink the array if size is 1/4 capacity.
     */
    private void conditionalShrink() {

        if (N > 0 && N < s.length / 4) {
            resize(s.length / 2);
        }
    }

    /**
     * Resize the array.
     * @param capacity
     */
    private void resize(int capacity) {

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0, j = tail; i < N; i++, j = getIncrementIndex(j)) {
            copy[i] = s[j];
        }
        s = copy;
        tail = 0;
        head = N;
    }

    /**
     * Gets the incremented index for the circularly buffered array.
     * @param index the index to be decremented.
     * @return integer
     */
    private int getIncrementIndex(int index) {

        if (index == s.length - 1) {
            return 0;
        } else {
            return ++index;
        }
    }

    /**
     * Gets the decremented index for the circularly buffered array.
     * @param index the index to be decremented.
     * @return integer
     */
    private int getDecrementIndex(int index) {
        
        if (index == 0) {
            return s.length - 1;
        } else {
            return --index;
        }
    }

    /**
     * Deque Iterator.
     */
    private class DequeIterator implements Iterator<Item> {

        private int current = head;
        
        /**
         * Is there a next element?
         */
        public boolean hasNext() {
            
            return current != tail;
        }

        /**
         * Not implemented.
         */
        public void remove() {
            
            throw new UnsupportedOperationException();
        }

        /**
         * Return the next item.
         */
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = getDecrementIndex(current);
            return s[current];
        }
    }

    public int arraySize() {
        return s.length;
    }

    /**
     * unit testing.
     * @param args
     */
    public static void main(String[] args) {

        int i;
        Deque<String> d = new Deque<String>();
        String testStr = "foo";

        for (i = 0; i < 20; i++) {
            d.addFirst(testStr);
            System.out.println(d.N);
            System.out.println(d.head);
            System.out.println(d.tail);
            System.out.println(d.arraySize());
            System.out.println("==========");
        }

        for (String s: d)
            System.out.println(s);

        for (i = 0; i < 20; i++) {
            System.out.println(d.removeFirst());
            System.out.println(d.N);
            System.out.println(d.head);
            System.out.println(d.tail);
            System.out.println(d.arraySize());
            System.out.println("==========");
        }
    }
}
