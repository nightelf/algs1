/**
 * 
 */
package clients;

import java.util.ArrayList;
import java.util.Arrays;

import structures.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

/**
 * @author jared
 *
 */
public class Fast {

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
    private Fast(String filename) {

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
        
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        double currentSlope = Double.NaN;
        double lastSlope = Double.NaN;
        Point[] sortPoints = new Point[points.length - 1];
        
        for (int i = 0; i < points.length; i++) {
            
            // get and sort points;
            for (int j = 0; j < points.length; j++) {
                
                if (j < i) {
                    sortPoints[j] = points[i];
                } else if (j < i) {
                    sortPoints[j] = points[i - 1];
                }
            }
            Arrays.sort(sortPoints, points[i].SLOPE_ORDER);
            
            
            for (int k = 0; k < sortPoints.length; k++) {
                
                currentSlope = points[i].slopeTo(sortPoints[k]);
                if (
                    Double.isNaN(lastSlope)
                    || currentSlope == lastSlope
                ) {
                    
                    tempPoints.add(sortPoints[k]);
                    lastSlope = currentSlope;
                } else {
                    
                	if (tempPoints.size() >= SET_SIZE) {
                		
                		printTuple(tempPoints.toArray());
                	}
                    lastSlope = Double.NaN;
                    tempPoints.clear();
                }
            }
        }
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
        
        Fast fast = new Fast(args[0]);
        fast.search();
        fast.show();
    }

}
