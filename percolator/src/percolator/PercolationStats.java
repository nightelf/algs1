package percolator;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    /**
     * The row or column size
     */
    private int rowSize;

    /**
     * The number of experiments
     */
    private int numExp;

    /**
     * Matrix size
     */
    private int size;

    /**
     * An array of the thresholds determined by the experiment
     */
    private double[] threshold;

    /**
     * Perform T independent computational experiments on an N-by-N grid
     * @param N row / column size
     * @param T number of experiments
     */
    public PercolationStats(int N, int T) {

        String message;
        if (N <= 0) {
            message = "1st parameter must be greater than zero.";
            throw new IllegalArgumentException(message);
        }
        if (T <= 0) {
            message = "2nd parameter must be greater than zero.";
            throw new IllegalArgumentException(message);
        }

        rowSize = N;
        size = rowSize * rowSize;
        numExp = T;
        threshold = new double[T];
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {

        return StdStats.mean(threshold);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {

        return StdStats.stddev(threshold);
    }

    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(numExp);
    }

    /**
     * returns upper bound of the 95% confidence intervall
     */
    public double confidenceHi() {

        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(numExp);
    }

    /**
     * test client, described below
     */
    public static void main(String[] args) {

        StdOut.println("Starting!");
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
//        int N = StdIn.readInt();
//        int T = StdIn.readInt();

        PercolationStats perkyStats = new PercolationStats(N, T);
        
        int x, y, numOpened;
        
        for (int i = 0; i < T; i++) {
            numOpened = 0;
            Percolation perky = new Percolation(N);
            while (!perky.percolates()) {
                x = StdRandom.uniform(perkyStats.rowSize) + 1;
                y = StdRandom.uniform(perkyStats.rowSize) + 1;
                if (!perky.isOpen(x, y)){
                    perky.open(x, y);
                    numOpened++;
                }
            }
            
            perkyStats.threshold[i] = (double) numOpened / perkyStats.size;
        }
        
        StdOut.println("average: " + perkyStats.mean());
        StdOut.println("stddev: " + perkyStats.stddev());
        StdOut.println("95% Confindence Low: " + perkyStats.confidenceLo());
        StdOut.println("95% Confindence High: " + perkyStats.confidenceHi());
        StdOut.println("Ending!");
    }
}
