package percolator;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
    
final public class Percolation {

    private WeightedQuickUnionUF uf;
    private int[] open;
    private int N;
    private int topIndex;
    private int bottomIndex;
    private int numSites;
    private static final int closedValue = 0;
    
    /**
     * The open value for the open array.
     */
    private static final int openValue = 1;

    /**
     * Create N-by-N grid, with all sites blocked.
     * @param N the row /column size
     */
    public Percolation(final int N) {

        this.N = N;
        numSites = N * N + 2;
        uf = new WeightedQuickUnionUF(numSites);
        open = new int[numSites];
        topIndex = 0;
        open[topIndex] = openValue;
        bottomIndex = numSites - 1;
        open[bottomIndex] = openValue;
    }

    /**
     * Open site (row i, column j) if it is not already.
     * @param i row coordinate
     * @param j column coordinate
     */
    public void open(final int i, final int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int p = getIndex(i, j);
        int q;
        if (open[p] == openValue) {
            return;
        } else {
            open[p] = openValue;
        }

        // union top
        if (i == 1) {
            uf.union(p, topIndex);
        } else {
            q = getIndex(i - 1, j);
            if (open[q] == openValue) {
                uf.union(p, q);
            }
        }

        // union bottom
        if (i == N) {
            uf.union(p, bottomIndex);
        } else {
            q = getIndex(i + 1, j);
            if (open[q] == openValue) {
                uf.union(p, q);
            }
        }

        // union left
        if (j != 1) {
            q = getIndex(i, j - 1);
            if (open[q] == openValue) {
                uf.union(p, q);
            }
        }

        // union right
        if (j != N) {
            q = getIndex(i, j + 1);
            if (open[q] == openValue) {
                uf.union(p, q);
            }
        }
    }

    /**
     * Is site (row i, column j) open?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    public boolean isOpen(final int i, final int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int index = getIndex(i, j);
        return open[index] == openValue;
    }

    /**
     * Is site (row i, column j) full?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    public boolean isFull(final int i, final int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int p = getIndex(i, j);
        return open[p] == openValue && uf.connected(p, topIndex);
    }

    /**
     * does the system percolate?
     * @return boolean
     */
    public boolean percolates() {

        return uf.connected(bottomIndex, topIndex);
    }

    /**
     * Gets the 1-dimensional index from the 2-D indicies.
     * @param i row index
     * @param j column index
     * @return integer
     */
    private int getIndex(final int i, final int j) {

        return (i - 1) * N + j;
    }

    /**
     * Is the point in bounds?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    private boolean isInBounds(final int i, final int j) {

        return i >= 1 && i <= N && j >= 1 && j <= N;
    }
}
