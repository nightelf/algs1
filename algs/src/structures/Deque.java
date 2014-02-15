package structures;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	/**
	 * Size of the deque
	 */
	private static final int capacity = 50;

	/**
	 * deque items
	 */
	private Item[] s;
	
	/**
	 * Size of the deque
	 */
	private int N = 0;

	/**
	 * index of the queue front
	 */
	private int front = 25;
	
	/**
	 * index of the queue back
	 */
	private int back = 25;
	
    /**
     * construct an empty deque.
     */
    public Deque() {
	
        s = (Item[]) new Object[capacity]
    }

   /**
    * is the deque empty?
    * @return
    */
    public boolean isEmpty() {
	
       return N == 0;
    }
   
    /**
     * return the number of items on the deque.
     * @return
     */
    public int size() {
	
        return N;
    }
   
    /**
     * insert the item at the front.
     * @param item
     */
    public void addFirst(Item item) {
	
        conditionalGrow();
		s[++front] = item;
    }
   
    /**
     * insert the item at the end.
     * @param item
     */
    public void addLast(Item item) {
	
		conditionalGrow();
        s[--back] = item;
    }
  
    /**
     * delete and return the item at the front.
     * @return
     */
    public Item removeFirst() {
        
		conditionalShrink();
		return s[front--];
    }
   
    /**
     * delete and return the item at the end.
     * @return
     */
    public Item removeLast() {
        
		conditionalShrink();
		return s[back++];
    }
    
    /**
     * return an iterator over items in order from front to end.
     */
    public Iterator<Item> iterator() {
        
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
	
		Item[] copy = (Item[]) new Object[capacity]
		for (int i = 0; i < N; i++) {
			copy[i] = s[i];
		}
		s = copy;
	}
   
    /**
     * unit testing.
     * @param args
     */
    public static void main(String[] args) {
        
    }
}
