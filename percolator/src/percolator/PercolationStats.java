package percolator;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * Percolation Statistics.
 */
public class PercolationStats {

    /**
     * Standard Normal Deviate.
     */
    public static final double STD_NORMAL_DEVIATE = 1.96;

    /**
     * The row or column size.
     */
    private int rowSize;

    /**
     * The number of experiments.
     */
    private int numExperiments;

    /**
     * Matrix size.
     */
    private int size;

    /**
     * Mean: cached so no need to recompute.
     */
    private double meanCalc = -1.0;

    /**
     * Standard Deviation: cached so no need to recompute.
     */
    private double stdDevCalc = -1.0;

    /**
     * An array of the thresholds determined by the experiment.
     */
    private double[] threshold;

    /**
     * Perform T independent computational experiments on an N-by-N grid.
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
        numExperiments = T;
        threshold = new double[T];

        computeThreshold();
    }

    /**
     * Computes the average threshold.
     */
    private void computeThreshold() {

        int x, y, numOpened;
        for (int i = 0; i < numExperiments; i++) {
            numOpened = 0;
            Percolation perky = new Percolation(rowSize);
            while (!perky.percolates()) {
                x = StdRandom.uniform(rowSize) + 1;
                y = StdRandom.uniform(rowSize) + 1;
                if (!perky.isOpen(x, y)) {
                    perky.open(x, y);
                    numOpened++;
                }
            }
            threshold[i] = (double) numOpened / size;
        }
    }

    /**
     * sample mean of percolation threshold.
     * @return double
     */
    public double mean() {

        if (meanCalc < 0.0) {
            meanCalc = StdStats.mean(threshold);
        }

        return meanCalc;
    }

    /**
     * sample standard deviation of percolation threshold.
     * @return double
     */
    public double stddev() {

        if (stdDevCalc < 0.0) {
            stdDevCalc = StdStats.stddev(threshold);
        }

        return stdDevCalc;
    }

    /**
     * returns lower bound of the 95% confidence interval.
     * @return double
     */
    public double confidenceLo() {
        return mean() - STD_NORMAL_DEVIATE
            * Math.sqrt(stddev()) / Math.sqrt(numExperiments);
    }

    /**
     * returns upper bound of the 95% confidence interval.
     * @return double
     */
    public double confidenceHi() {

        return mean() + STD_NORMAL_DEVIATE
            * Math.sqrt(stddev()) / Math.sqrt(numExperiments);
    }

    /**
     * test client, described below.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats perkyStats = new PercolationStats(N, T);
        System.out.println("average: " + perkyStats.mean());
        System.out.println("stddev: " + perkyStats.stddev());
        System.out.println("95% Confindence Low: "
            + perkyStats.confidenceLo());
        System.out.println("95% Confindence High: "
            + perkyStats.confidenceHi());
    }
}
