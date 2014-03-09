package puzzle8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Board
 */
public class Board {

    /**
     * blocks.
     */
    final private int[][] blocks;

    /**
     * Size.
     */
    final private int N;

    /**
     * Empty Index
     */
    final private int[] emptyIndex;

    /**
     * Is The Goal.
     */
    private Boolean isTheGoal;

    /**
     * Cached Hamming Score.
     */
    private int hammingScore = -1;

    /**
     * Cached Manhattan Score.
     */
    private int manhattanScore = -1;

    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {

        this.blocks = blocks;
        N = this.blocks.length;
        emptyIndex = new int[2];
        emptyIndex[0] = -1;
        hammingScore = -1;
        manhattanScore = -1;
    }

    private int[] getIndicies(int number) {

        int[] ret = new int[2];
        if (number == 0) {
            ret[0] = N - 1;
            ret[1] = N - 1;
        } else {
            ret[0] = (number - 1) / N;
            ret[1] = number % N;
            ret[1] = ret[1] == 0 ? N - 1 : ret[1] - 1;
        }
        return ret;
    }

    /**
     * Get copy of blocks int[][]
     * @return int[][]
     */
    private int[][] getBlocksCopy() {

        int[][] newBlocks =  new int[N][N];
        for (int i = 0; i < N; i++)
            newBlocks[i] = Arrays.copyOf(blocks[i], N);
        return newBlocks;
    }

    /**
     * Board dimension N.
     * @return
     */
    public int dimension() {
        return N;
    }

    /**
     * Number of blocks out of place
     * @return
     */
    public int hamming() {

        if (-1 == hammingScore) {
            int n = 1;
            hammingScore = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] != n++ && blocks[i][j] != 0) {
                        hammingScore++;
                    }
                }
            }
        }
        return hammingScore;
    }

    /**
     * Sum of Manhattan distances between blocks and goal.
     * @return
     */
    public int manhattan() {

        if (-1 == manhattanScore) {
            int[] goalIndicies;
            manhattanScore = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] != 0) {
                        goalIndicies = getIndicies(blocks[i][j]);
                        manhattanScore += Math.abs(i - goalIndicies[0]) + Math.abs(j - goalIndicies[1]);
                    }
                }
            }
        }
        return manhattanScore;
    }

    /**
     * Is this board the goal board?
     * @return
     */
    public boolean isGoal() {

        if (null == isTheGoal) {
            
        	int max = N * N;
            int n = 1;
            isTheGoal = true;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N && n < max; j++) {
                    if (blocks[i][j] != n++) {
                        
                        isTheGoal = false;
                    }
                }
            }
        }
        
        return isTheGoal;
    }

    /**
     * A board obtained by exchanging two adjacent blocks in the same row.
     * @return
     */
    public Board twin() {

        setEmptyIndex();
        int i = emptyIndex[0] != 0 ? 0 : 1;
        int j = N - 1;
        int k = N - 2;
        int temp;
        int[][] blocksCopy = getBlocksCopy();

        // swap
        temp = blocksCopy[i][j];
        blocksCopy[i][j] = blocksCopy[i][k];
        blocksCopy[i][k] = temp;

        return new Board(blocksCopy);
    }
    
    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {

        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * all neighboring boards.
     * @return Iterator<Board>
     */
    public Iterable<Board> neighbors() {

        return new NeighborIterator();
    }

    /**
     * String representation of the board (in the output format specified below).
     */
    public String toString() {

        String aString = Integer.toString(N) + "\n";
        String blockString;
        int n = 1 + (int) Math.floor(Math.log10(N * N));

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++ ) {

                blockString = String.format("%" + Integer.toString(n) + "s", blocks[i][j]);
                aString = aString + " " + blockString;
            }
            aString = aString + "\n";
        }
        return aString;
    }

    /**
     * Gets the index of the empty square.
     * @return int[][]
     */
    private void setEmptyIndex() {

        if (emptyIndex[0] == -1) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] == 0) {
                        emptyIndex[0] = i;
                        emptyIndex[1] = j;
                    }
                }
            }
        }
    }

    /**
     * Iterates over an array of Board which are neighbors of this object.
     */
    private class NeighborIterator implements Iterable<Board> {

        /**
         * The neighbor boards to the current Board.
         */
        private ArrayList<Board> boards = new ArrayList<Board>();

        /**
         * Constructor.
         */
        public NeighborIterator() {

            Board newBoard;
            setEmptyIndex();
            int[][] neighbors = {
                    {emptyIndex[0], emptyIndex[1] - 1}, // Top
                    {emptyIndex[0] + 1, emptyIndex[1]}, // Right
                    {emptyIndex[0], emptyIndex[1] + 1}, // Bottom
                    {emptyIndex[0] - 1, emptyIndex[1]}, // Left
            };

            for (int i[] : neighbors) {
                newBoard = getNeighborBoard(i[0], i[1]);
                if (null != newBoard)
                    boards.add(newBoard);
            }
        }

        public Iterator<Board> iterator() {

            return boards.iterator();
        }

        /**
         * Gets a neighbor Board.
         * @param i 1d array index
         * @param j 2d array index
         * @return Board
         */
        private Board getNeighborBoard(int i, int j) {

            if (isValidIndex(i) && isValidIndex(j)) {
                int[][] newBlocks = getBlocksCopy();
                // swap
                newBlocks[emptyIndex[0]][emptyIndex[1]] = newBlocks[i][j];
                newBlocks[i][j] = 0;
                return new Board(newBlocks);
            }
            return null;
        }

        /**
         * Is the index valid.
         * @param index the index
         * @return Boolean
         */
        private boolean isValidIndex(int index) {

            return index < N && index >= 0;
        }
    }

    public static void main(String[] args) {

        int[][] testBlocks= {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 },
        };
        
        Board testBoard = new Board(testBlocks); 
        assert testBoard.hamming() == 5;
        assert testBoard.manhattan() == 10;
        assert testBoard.isGoal() == false;
        
        int[][] testBlocks2= {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 },
            };
        
        Board testBoard2 = new Board(testBlocks2);
        assert testBoard2.hamming() == 0;
        assert testBoard2.manhattan() == 0;
        assert testBoard2.isGoal() == true;
        
        int[][] testBlocks3= {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 8, 7, 0 },
            };
        Board testBoard3 = new Board(testBlocks3); 
        assert testBoard3.hamming() == 2;
        assert testBoard3.manhattan() == 2;
        assert testBoard3.isGoal() == false;
    }
}
