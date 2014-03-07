package puzzle8;

import java.util.Comparator;

import structures.Point;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Solver class
 */
public class Solver {

    Board board;
    int moves = 0;
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        board = initial;
        Board prev;
        
        MinPQ<SearchNode> q = new MinPQ<SearchNode>(new ByManhattan());
    }
    
    /**
     * Is the initial board solvable?
     * @return boolean
     */
    public boolean isSolvable() {
        
        return true;
    }
    
    /**
     * Min number of moves to solve initial board; -1 if no solutio
     * @return
     */
    public int moves() {
        
        return 1;
    }
    
    /**
     * Sequence of boards in a shortest solution; null if no solution
     * @return Iterable<Board>
     */
    public Iterable<Board> solution() {


    }
    
    /**
     * The search node
     */
    private class SearchNode {
        
        /**
         * The board.
         */
        public Board board;
        
        /**
         * The previous board.
         */
        public Board prev;
        
        /**
         * A count of the number of moves.
         */
        public Board moves;
        
        /**
         * 
         * @param board
         */
        SearchNode(Board board, Board prev, int moves) {
            
            this.board = board;
        }
    }
    
    /**
     * Compares the 2 points by the reference.
     */
    private class ByManhattan implements Comparator<SearchNode> {

        /**
         * Compares the points.
         * @param p1 point 1
         * @param p2 point 2
         */
        public int compare(SearchNode n1, SearchNode n2) {

            int priority1 = n1.board.manhattan();
            int priority2 = n2.board.manhattan();

            if (priority1 < priority2) {
                return -1;
            } else if (priority1 > priority2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    /**
     * Compares the 2 points by the reference.
     */
    private class ByHamming implements Comparator<SearchNode> {

        /**
         * Compares the points.
         * @param p1 point 1
         * @param p2 point 2
         */
        public int compare(SearchNode n1, SearchNode n2) {

            int priority1 = n1.board.hamming();
            int priority2 = n2.board.hamming();

            if (priority1 < priority2) {
                return -1;
            } else if (priority1 > priority2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args) {
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
