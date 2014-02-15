package structures;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    /**
     * Size of the deque.
     */
    private static final int CAPACITY_START = 10;

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
     * construct an empty deque.
     */
    public Deque() {

        s = (Item[]) new Object[CAPACITY_START];
    }

    /**
     * is the deque empty?
     * 
     * @return
     */
    public boolean isEmpty() {

        return N == 0;
    }

    /**
     * return the number of items on the deque.
     * 
     * @return
     */
    public int size() {

        return N;
    }

    /**
     * insert the item at the head.
     * 
     * @param item
     */
    public void addFirst(Item item) {

        conditionalGrow();
        if (head == s.length - 1) {
        	s[head] = item;
        	head = 0;
        } else {
        	s[head++] = item;
        }
        N++;
    }

    /**
     * insert the item at the end.
     * 
     * @param item
     */
    public void addLast(Item item) {

        conditionalGrow();
        if (tail == 0) {
        	s[tail] = item;
        	head = s.length - 1;
        }
        s[tail--] = item;
        N++;
    }

    /**
     * delete and return the item at the head.
     * 
     * @return
     */
    public Item removeFirst() {

        conditionalShrink();
        if (head == 0) {

        	head = s.length - 1;
        	N--;
        	return s[0];
        } else {
        
        	N--;
        	return s[head--];
        }

    }

    /**
     * delete and return the item at the end.
     * 
     * @return
     */
    public Item removeLast() {

        conditionalShrink();
        if (tail == s.length - 1) {

        	tail = 0;
        	N--;
        	return s[s.length - 1];
        } else {
        	
        	N--;
        	return s[tail++];
        }

    }

    /**
     * return an iterator over items in order from head to end.
     */
    public Iterator<Item> iterator() {
        
        return new DequeIterator();
    }

    private void conditionalGrow() {

        if (N == s.length) {
            resize(2 * s.length);
        }
    }

    private void conditionalShrink() {

        if (N > 0 && N == s.length / 4) {
            resize(s.length / 2);
        }
    }

    private void resize(int capacity) {
    
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0, j = tail; i < N; i++) {
            copy[i] = s[j];
        }
        s = copy;
    }

    /**
     * Deque Iterator.
     */
    private class DequeIterator implements Iterator<Item> {

        /**
         * Is there a next element?
         */
        public boolean hasNext() {
            return true;
        }

        /**
         * Not implemented.
         */
        public void remove() {

        }

        /**
         * Return the next item.
         */
        public Item next() {
            
            return s[0];
        }
    }

    /**
     * unit testing.
     * 
     * @param args
     */
    public static void main(String[] args) {

    }
}
