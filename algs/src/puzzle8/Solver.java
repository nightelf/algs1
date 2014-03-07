package puzzle8;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Solver class
 */
public class Solver {

        
        /**
         * find a solution to the initial board (using the A* algorithm)
         * @param initial
         */
        public Solver(Board initial) {

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
