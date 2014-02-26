/**
 * 
 */
package clients;

import java.util.Arrays;

import structures.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Brute force class to find collinear points
 */
public class Brute {

    /**
     * The Set Size
     */
    final private static int SET_SIZE = 4;

    /**
     * An array of the points
     */
    private Point[] points;

    /**
     * Constructor
     * @param filename the filename
     */
    private Brute(String filename) {

        setupDraw();
        points = read(filename);
    }

    /**
     * Set up the Drawing.
     */
    private void setupDraw() {

        //rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(0);
    }

    /**
     * Show the points and lines.
     */
    private void show() {

        StdDraw.show(0);
    }

    /**
     * Are the points collinear?
     * @param anyPoints any set of points
     * @return
     */
    private boolean isCollinear(Point[] anyPoints) {

        return anyPoints[0].slopeTo(anyPoints[1]) ==
            anyPoints[0].slopeTo(anyPoints[2]) &&
            anyPoints[0].slopeTo(anyPoints[1]) ==
            anyPoints[0].slopeTo(anyPoints[3]);
    }

    /**
     * Search the points.
     */
    private void search() {

        int loopIndex = points.length - SET_SIZE;
        Point[] myPoints = new Point[4];
        for (int i = 0; i <= loopIndex; i++) {
            for (int j = i + 1; j <= loopIndex + 1; j++) {
                for (int k = j + 1; k <= loopIndex + 2; k++) {
                    for (int l = k + 1; l <= loopIndex + 3; l++) {
                        if (j != i && k != j && l != k) {

                            // collect points
                            myPoints[0] = points[i];
                            myPoints[1] = points[j];
                            myPoints[2] = points[k];
                            myPoints[3] = points[l];
                            
                            if (isCollinear(myPoints)) {

                                printTuple(myPoints);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints the tuple.
     * @param collinearPoints the points (array[4]).
     */
    private void printTuple(Point[] collinearPoints) {

        Arrays.sort(collinearPoints);
        collinearPoints[0].drawTo(collinearPoints[3]);
        System.out.println(
            String.format(
                "%s -> %s -> %s -> %s",
                collinearPoints[0].toString(),
                collinearPoints[1].toString(),
                collinearPoints[2].toString(),
                collinearPoints[3].toString()
            )
        );
    }

    /**
     * Reads the filename. Returns the points.
     * @param filename
     * @return
     */
    private Point[] read (String filename) {

        In in = new In(filename);
        int N = in.readInt();
        Point[] thePoints = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            thePoints[i] = p;
            p.draw();
        }
        return thePoints;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String filename = args[0];
        Brute brute = new Brute(filename);
        brute.search();
        brute.show();
    }
}
