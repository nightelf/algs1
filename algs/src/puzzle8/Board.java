package puzzle8;

public class Board {

	final private int[][] blocks;
	
	final private int[][] goal;
	
	final private int N;
	
    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        
    	this.blocks = blocks;
    	this.goal = new int[this.blocks.length][this.blocks.length];
    	N = this.blocks.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                goal[i][j] = N * i + j + 1;
        }
    }

    private int[] getIndicies(key) {
    	
    	int[] ret = new int[2];
    	ret[0] = key / N;
    	ret[1] = key / N;
    	
    	return ret
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
        
    }
    
    /**
     * Sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
        
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
        // 
    }
    
    /**
     * all neighboring boards
     * @return Iterable<Board>
     */
    public Iterable<Board> neighbors() {
        // 
    }
    
    /**
     * String representation of the board (in the output format specified below)
     */
    public String toString() {
        
    }
}
