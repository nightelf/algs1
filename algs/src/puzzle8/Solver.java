package puzzle8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Solver class
 */
public class Solver {

    
    Board board;
    
    /**
     * Number of moves
     */
    int moves = 0;
    
    /**
     * List of solution boards.
     */
    ArrayList<Board> solution = new ArrayList<Board>();
    
    /**
     * Is the 8-puzzle solvable?
     */
    Boolean isSolvable;
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        
        board = initial;
        Board twin = board.twin();
        
        SearchNode node = new SearchNode(board, null, 0);
        MinPQ<SearchNode> q = new MinPQ<SearchNode>(new ByManhattan());
        q.insert(node);
        System.out.println(node.board.toString());
        SearchNode twinNode = new SearchNode(twin, null, 0);
        MinPQ<SearchNode> qTwin = new MinPQ<SearchNode>(new ByManhattan());
        qTwin.insert(twinNode);
        System.out.println(twinNode.board.toString());
        while (!node.board.isGoal() && !twinNode.board.isGoal()) {
            
            node = q.delMin();
            twinNode = qTwin.delMin();
            solution.add(node.board);
            
            
            for (Board i: node.board.neighbors()) {
                node = new SearchNode(i, node.board, node.moves + 1);
                if (!node.board.equals(node.prev)) {
                    q.insert(node);
                }
            }
            
            for (Board i: twinNode.board.neighbors()) {
                twinNode = new SearchNode(i, twinNode.board, twinNode.moves + 1);
                if (!twinNode.board.equals(twinNode.prev)) {
                    q.insert(twinNode);
                }
            }
        }
        
        if (node.board.isGoal()) {
            isSolvable = true;
            moves = node.moves;
        } else {
            isSolvable = false;
            moves = twinNode.moves;
        }
    }
    
    /**
     * Is the initial board solvable?
     * @return boolean
     */
    public boolean isSolvable() {
        
        return isSolvable;
    }
    
    /**
     * Min number of moves to solve initial board; -1 if no solutio
     * @return
     */
    public int moves() {
        
        return moves;
    }
    
    /**
     * Sequence of boards in a shortest solution; null if no solution
     * @return Iterable<Board>
     */
    public Iterable<Board> solution() {

        return new SolutionIterator();
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
        public int moves;
        
        /**
         * 
         * @param board
         */
        SearchNode(Board board, Board prev, int moves) {
            
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }
    }
    

    /**
     * Iterates over an ArrayList of Board.
     */
    private class SolutionIterator implements Iterable<Board> {

    	/**
    	 * Gets an iterator
    	 */
        public Iterator<Board> iterator() {

            return solution.iterator();
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

            int priority1 = n1.board.manhattan() + n1.moves;
            int priority2 = n2.board.manhattan() + n2.moves;

            if (priority1 > priority2) {
                return -1;
            } else if (priority1 < priority2) {
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

            int priority1 = n1.board.hamming() + n1.moves;
            int priority2 = n2.board.hamming() + n2.moves;

            if (priority1 > priority2) {
                return -1;
            } else if (priority1 < priority2) {
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
