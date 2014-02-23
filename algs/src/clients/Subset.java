package clients;

import structures.RandomizedQueue;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * Class Subset
 */
public class Subset {

    /**
     * Read in strings. print subset.
     * @param args the arguments.
     */
    public static void main(String[] args) {
        
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        
        while(!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
