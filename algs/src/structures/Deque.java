package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Deque class.
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * Size of the deque.
     */
    private static final int CAPACITY_START = 4;

    /**
     * The shrink divisor. 1/4 the size of hte array.
     */
    private static final int SHRINK_DIVISOR = 4;

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
     * Insert the item at the head.
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
     * Delete and return the item at the head.
     * @return Item
     */
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        conditionalShrink();
        head = getDecrementIndex(head);
        N--;
        return s[head];
    }

    /**
     * Delete and return the item at the tail.
     * @return Item
     */
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        conditionalShrink();
        int oldTail = tail;
        tail = getIncrementIndex(tail);
        N--;
        return s[oldTail];
    }

    /**
     * return an iterator over items in order from head to end.
     * @return DequeIterator
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

        if (N > 0 && N < s.length / SHRINK_DIVISOR) {
            resize(s.length / 2);
        }
    }

    /**
     * Resize the array.
     * @param capacity the capacity of the array.
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

        /**
         * The current position in the iterator.
         */
        private int current = head;

        /**
         * Is there a next element?
         * @return boolean
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
         * @return Item
         */
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = getDecrementIndex(current);
            return s[current];
        }
    }

    /**
     * Unit testing.
     * @param args the arguments.
     */
    public static void main(String[] args) {

        Deque<String> d = new Deque<String>();

        assert d.size() == 0;
        assert d.isEmpty() == true;
        d.addFirst("Jack");
        assert d.isEmpty() == false;
        assert d.removeFirst() == "Jack";
        assert d.isEmpty() == true;
        assert d.size() == 0;
        d.addFirst("and");
        assert d.size() == 1;
        assert d.removeLast() == "and";
        assert d.size() == 0;
        d.addLast("Jill");
        assert d.size() == 1;
        assert d.removeFirst() == "Jill";
        assert d.size() == 0;
        try {
            System.out.println(d.removeFirst());
            assert false;
        } catch (NoSuchElementException e) {
            assert true;
        }
        d.addLast("went");
        d.addLast("to");
        assert d.size() == 2;
        d.addLast("fetch");
        d.addLast("a");
        d.addLast("pail");
        assert d.size() == 5;
        d.addFirst("of water");
        assert d.removeFirst() == "of water";
        assert d.removeLast() == "pail";
        assert d.size() == 4;
        assert d.isEmpty() == false;
        for (Iterator<String> words = d.iterator(); words.hasNext();) {
            System.out.println(words.next());
          }
        System.out.println("END");
    }
}
