package puzzle8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    final private int[][] blocks;
    
    final private int[][] goal;
    
    final private int N;

    final private int[] emptyIndex = new int[2];
    
    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        
        this.blocks = blocks;
        this.goal = new int[this.blocks.length][this.blocks.length];
        N = this.blocks.length;

        // generate the target board
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)    {
                goal[i][j] = N * i + j + 1;

                // set the empty index
                if (blocks[i][j] == 0) {
                    emptyIndex[0] = i;
                    emptyIndex[1] = j;
                }

            }
        }
        goal[N - 1][N - 1] = 0;
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
        
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (goal[i][j] != blocks[i][j]) {
                    hamming++;
                }
            }
        }
        return hamming;
    }
    
    /**
     * Sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
        int x, y;
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	
                	manhattan += (i - x) + (j - y);
                
            }
        }
    }
    
    /**
     * Is this board the goal board?
     * @return
     */
    public boolean isGoal() {
        
    }
    
    /**
     * A board obtained by exchanging two adjacent blocks in the same row
     * @return
     */
    public Board twin() {
        // 
    }
    
    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {

        return blocks.equals(y)
    }
    
    /**
     * all neighboring boards
     * @return Iterator<Board>
     */
    public Iterator<Board> neighbors() {

        return new NeighborIterator();
    }

    /**
     * String representation of the board (in the output format specified below)
     */
    public String toString() {

        String aString = "";
        int i, j;
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++ ) {
                aString = aString + " " + a[i][j];
            }
            aString = aString + "\n";
        }
        return aString;
    }

    /**
     * Iterates over an array of Board which are neighbors of this object.
     */
    private class NeighborIterator implements Iterator<Board> {

        /**
         * The neighbor boards to the current Board.
         */
        private Board[] boards;

        /**
         * The current index.
         */
        private int current = 0;

        /**
         * Constructor.
         */
        public NeighborIterator() {

            Board newBoard;
            ArrayList<Board> genBoards = new ArrayList<Board>();

            int[][] neighbors = {
                    {emptyIndex[0], emptyIndex[1] - 1}, // Top
                    {emptyIndex[0] + 1, emptyIndex[1]}, // Right
                    {emptyIndex[0], emptyIndex[1] + 1}, // Bottom
                    {emptyIndex[0] - 1, emptyIndex[1]}, // Left
            };

            for (int i[] : neighbors) {
                newBoard = getNeighborBoard(i[0], i[1]);
                if (null != newBoard)
                    genBoards.add(newBoard);
            }
            boards = (Board[]) genBoards.toArray();
        }

            /**
             * Is there a next element?
             * @return boolean
             */
            public boolean hasNext() {

                return current < boards.length - 1;
            }

            /**
             * Not implemented.
             */
            public void remove() {

                throw new UnsupportedOperationException();
            }

            /**
             * Return the next Board.
             * @return Board
             */
            public Board next() {

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return boards[current++];
            }

        /**
         * Gets a neighbor Board.
         * @param i 1d array index
         * @param j 2d array index
         * @return Board
         */
        private Board getNeighborBoard(int i, int j) {

            if (isValidIndex(i) && isValidIndex(j)) {
                int[][] newBlocks =  new int[N][N];
                for (int k = 0; k < N; k++)
                    newBlocks[k] = Arrays.copyOf(blocks[k], N);
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
}
