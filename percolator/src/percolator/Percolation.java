/**
 * Percolator package.
 */
package percolator;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public final class Percolation {

    /**
     * An instance of the WeightedQuickUnionUF class.
     */
    private WeightedQuickUnionUF unionFind;

    /**
     * An array telling which sites are open.
     */
    private int[] open;

    /**
     * The row or column size for the square matrix.
     */
    private int rowSize;

    /**
     * The index of the top site.
     */
    private int topIndex;

    /**
     * The index of the bottom site.
     */
    private int bottomIndex;

    /**
     * the total number of sites (N + 2).
     */
    private int numSites;

    /**
     * The open value for the open array.
     */
    private static final int OPEN_VALUE = 1;

    /**
     * Create N-by-N grid, with all sites blocked.
     * @param nSize the row /column size
     */
    public Percolation(int N) {

        this.rowSize = N;
        numSites = rowSize * rowSize + 2;
        unionFind = new WeightedQuickUnionUF(numSites);
        
        open = new int[numSites];
        topIndex = 0;
        open[topIndex] = OPEN_VALUE;
        
        bottomIndex = numSites - 1;
        open[bottomIndex] = OPEN_VALUE;
    }

    /**
     * Open site (row i, column j) if it is not already.
     * @param i row coordinate
     * @param j column coordinate
     */
    public void open(int i, int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int p = getIndex(i, j);
        int q;
        if (open[p] == OPEN_VALUE) {
            return;
        } else {
            open[p] = OPEN_VALUE;
        }

        // union top
        if (i == 1) {
            unionFind.union(p, topIndex);
        } else {
            q = getIndex(i - 1, j);
            if (open[q] == OPEN_VALUE) {
                unionFind.union(p, q);
            }
        }

        // union bottom
        if (i == rowSize) {
            unionFind.union(p, bottomIndex);
        } else {
            q = getIndex(i + 1, j);
            if (open[q] == OPEN_VALUE) {
                unionFind.union(p, q);
            }
        }

        // union left
        if (j != 1) {
            q = getIndex(i, j - 1);
            if (open[q] == OPEN_VALUE) {
                unionFind.union(p, q);
            }
        }

        // union right
        if (j != rowSize) {
            q = getIndex(i, j + 1);
            if (open[q] == OPEN_VALUE) {
                unionFind.union(p, q);
            }
        }
    }

    /**
     * Is site (row i, column j) open?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    public boolean isOpen(int i, int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int index = getIndex(i, j);
        return open[index] == OPEN_VALUE;
    }

    /**
     * Is site (row i, column j) full?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    public boolean isFull(int i, int j) {

        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int p = getIndex(i, j);
        return open[p] == OPEN_VALUE && unionFind.connected(p, topIndex);
    }

    /**
     * does the system percolate?
     * @return boolean
     */
    public boolean percolates() {

        return unionFind.connected(bottomIndex, topIndex);
    }

    /**
     * Gets the 1-dimensional index from the 2-D indicies.
     * @param i row index
     * @param j column index
     * @return integer
     */
    private int getIndex(int i, int j) {

        return (i - 1) * rowSize + j;
    }

    /**
     * Is the point in bounds?
     * @param i row index
     * @param j column index
     * @return boolean
     */
    private boolean isInBounds(int i, int j) {

        return i >= 1 && i <= rowSize && j >= 1 && j <= rowSize;
    }
}
