package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdRandom;

/**
 * Randomized Queue.
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * Size of the Randomized Queue.
     */
    private static final int CAPACITY_START = 4;

    /**
     * The shrink divisor. 1/4 the size of the array.
     */
    private static final int SHRINK_DIVISOR = 4;

    /**
     * Randomized queue items.
     */
    private Item[] s;

    /**
     * Size of the randomized queue.
     */
    private int N = 0;

    /**
     * construct an empty randomized queue.
     */
    public RandomizedQueue() {

        s = (Item[]) new Object[CAPACITY_START];
    }

   /**
    * is the queue empty?
    * @return boolean
    */
    public boolean isEmpty() {

        return N == 0;
    }

    /**
     * return the number of items on the queue.
     * @return int
     */
    public int size() {

        return N;
    }

    /**
     * Add the item.
     * @param item the item.
     */
    public void enqueue(Item item) {

        if (null == item) {
            throw new NullPointerException();
        }
        if (N == s.length) {
            resize(N * 2);
        }
        s[N++] = item;
    }

    /**
     * delete and return a random item.
     * @return Item
     */
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int removedIndex = StdRandom.uniform(N);
        Item item = s[removedIndex];
        reorder(removedIndex);

        if (N > 0 && N < s.length / SHRINK_DIVISOR) {
            resize(s.length / 2);
        }

        return item;
    }

    /**
     * return (but do not delete) a random item.
     * @return Item
     */
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int sampledIndex = StdRandom.uniform(N);
        return s[sampledIndex];
    }

    /**
     * return an independent iterator over items in random order.
     * @return RandomizedQueueIterator;
     */
    public Iterator<Item> iterator() {

        return new RandomizedQueueIterator();
    }

    /**
     * Grow the array.
     * @param capacity the capacity.
     */
    private void resize(int capacity) {

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    /**
     * Resize the array.
     * @param removedIndex the removed index.
     */
    private void reorder(int removedIndex) {

        int lastIndex = N - 1;
        s[removedIndex] = s[lastIndex];
        s[lastIndex] = null;
        --N;
    }

    /**
     * Randomized Queue Iterator.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        /**
         * the index of the current element.
         */
        private int current = N - 1;

        /**
         * Indexes pointing to the outer class queue.
         */
        private int[] indexes;

        /**
         * Constructor
         */
        public RandomizedQueueIterator() {
            
            indexes = new int[N];
            for (int i = 0; i < N; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        /**
         * Is there a next element?
         * @return boolean
         */
        public boolean hasNext() {

            return current >= 0;
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

            return s[indexes[current--]];
        }
    }

    /**
     * unit testing.
     * @param args the arguments.
     */
    public static void main(String[] args) {

        RandomizedQueue<String> d = new RandomizedQueue<String>();

        assert d.size() == 0;
        assert d.isEmpty() == true;
        d.enqueue("Jack");
        assert d.size() == 1;
        assert d.isEmpty() == false;
        assert d.dequeue().equals("Jack");
        assert d.isEmpty() == true;
        assert d.size() == 0;

        d.enqueue("and");
        d.enqueue("Jill");
        assert d.size() == 2;
        String foo = d.dequeue();
        assert foo.equals("Jill") || foo.equals("and");
        assert d.size() == 1;
        foo = d.dequeue();
        assert foo.equals("Jill") || foo.equals("and");
        assert d.size() == 0;
        try {
            System.out.println(d.dequeue());
            assert false;
        } catch (NoSuchElementException e) {
            assert true;
        }
        d.enqueue("went");
        d.enqueue("to");
        assert d.size() == 2;
        d.enqueue("fetch");
        d.enqueue("a");
        d.enqueue("pail");
        assert d.size() == 5;
        System.out.println(d.dequeue());
        assert d.size() == 4;
        for (Iterator<String> words = d.iterator(); words.hasNext();) {
            System.out.println(words.next());
        }
        for (Iterator<String> werds = d.iterator(); werds.hasNext();) {
            werds.next();
            System.out.println(d.sample());
        }
        
        RandomizedQueue<String> g = new RandomizedQueue<String>();
        for(int i = 0; i < 1000; i++) {
            g.enqueue("juicy");
        }
        int h = 0;
        for (Iterator<String> testy = g.iterator(); testy.hasNext();) {
            h++;
        }
        assert h == 1000;
        System.out.println("END");
    }
}
