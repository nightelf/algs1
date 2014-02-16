package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    /**
     * Size of the deque.
     */
    private static final int CAPACITY_START = 4;
    
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
    * @return
    */
    public boolean isEmpty() {
       
        return N == 0;
    }
   
    /**
     * return the number of items on the queue.
     * @return
     */
    public int size() {
        
        return N;
    }
   
    /**
     * add the item
     * @param item the item
     */
    public void enqueue(Item item) {
        
        conditionalResizeUp();
        s[N++] = item;
    }
  
    /**
     * delete and return a random item.
     * @return
     */
    public Item dequeue() {
        
        int removedIndex = StdRandom.uniform(N);
        Item item = s[removedIndex];
        if (N > 0 && N < s.length / 4) {
            resizeDown(removedIndex, s.length / 2);
        } else {
            reorder(removedIndex);
        }
        return item;
    }
   
    /**
     * return (but do not delete) a random item.
     * @return
     */
    public Item sample() {
        
        int sampledIndex = StdRandom.uniform(N);
        return s[sampledIndex];
    }
    
    /**
     * return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {
        
        return new RandomizedQueueIterator();
    }
   
    /**
     * Conditionally grow the array if size is at capacity.
     * @param capacity the capacity.
     */ 
    private void conditionalResizeUp() {

        if (N == s.length) {
            int capacity = N * 2;
            Item[] copy = (Item[]) new Object[capacity];
            for (int i = 0; i < N; i++) {
                copy[i] = s[i];
            }
            s = copy;
        }
    }

    
    /**
     * Resize the array.
     * @param capacity
     */
    private void resizeDown(int removedIndex, int capacity) {

        int i;
        Item[] copy = (Item[]) new Object[capacity];
        for (i = 0; i < removedIndex; i++) {
            copy[i] = s[i];
        }
        if (removedIndex != N - 1) {
            for (i = removedIndex + 1; i < N; i++) {
                copy[i] = s[i];
            }
        }
        s = copy;
        --N;
    }
    
    /**
     * Resize the array.
     * @param removedIndex
     */
    private void reorder(int removedIndex) {

        int lastIndex = N - 1;
        for (int i = removedIndex; i < lastIndex; i++) {
            s[i] = s[i + 1];
        }
    }
    
    /**
     * Randomized Queue Iterator.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

    	/**
    	 * the index of the current element.
    	 */
        private int current = N;
        
        /**
         * Is there a next element?
         */
        public boolean hasNext() {
            
            return current > 0;
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
            return s[--current];
        }
    }
    
    /**
     * unit testing.
     * @param args
     */
    public static void main(String[] args) {
        
        int i;
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        String testStr = "foo";

        for (i = 0; i < 20; i++) {
        	r.enqueue(testStr);
        }
        
        for (i = 0; i < 20; i++) {
        	String foo = r.dequeue();
        	System.out.println(foo);
        }
    }
}
