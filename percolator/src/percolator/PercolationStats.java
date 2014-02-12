package percolator;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;



public class PercolationStats {

    /**
     * perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
    
    }
    
    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return 0d;
    }
    
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return 0d;
    }
    
    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo()
    {
        return 0d;
    }
    
    /**
     * returns upper bound of the 95% confidence intervall
     */
    public double confidenceHi() {
    	return 0d;
    }
    
    /**
     * test client, described below
     */
    public static void main(String[] args) {
    	int count = 0;
    	StdOut.println("Starting!");
        int N = StdIn.readInt();
        Percolation perky = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            perky.open(i, j);
            
            StdOut.println(i + " " + j);
            if (perky.percolates()) break;
            count++;
            //if (count == 9) break;
            
        }
        if (perky.percolates()) {
            StdOut.println("It Percolates!");
        } else {
        	StdOut.println("It Don't Percolate!");
        }
        
        StdOut.println("Ending!");
    }
}
