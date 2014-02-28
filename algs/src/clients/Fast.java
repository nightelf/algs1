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
 * The fast collinear point finder.
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
     * A cache of found collinear points.
     */
    private ArrayList<Point[]> foundCache = new ArrayList<Point[]>();

    /**
     * Pseudo constructor 'cause of silly code inspector.
     * @param filename the filename
     */
    private void init(String filename) {

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
    private void printTupleConditionally(Point[] collinearPoints) {

        Arrays.sort(collinearPoints);
            if (!isDegenerateSlope(collinearPoints)) {
                collinearPoints[0].drawTo(
                    collinearPoints[collinearPoints.length -1]
                );
                String part;
                String whole = "";
                for (int i = 0; i < collinearPoints.length; i++) {
                    part = i == collinearPoints.length - 1 ? "%s" : "%s -> ";
                    whole += String.format(part ,collinearPoints[i].toString());
                }
                System.out.println(whole);
        }
    }

    /**
     * Is the slope degenerate?
     * @param anyPoints any set of points
     * @return
     */
    private boolean isDegenerateSlope(Point[] anyPoints) {

        boolean isDegenerate = false;
        for (Point[] thePoints : foundCache) {
            
            if (Arrays.equals(anyPoints, thePoints)) {
                isDegenerate = true;
                break;
            }
        }
        if (!isDegenerate) foundCache.add(anyPoints);

        return isDegenerate;
    }

    /**
     * Gets the sorted points.
     * @param skipIndex the index currently being used as comparator.
     * @return
     */
    private Point[] getSortedPoints(int skipIndex) {

        Point[] sortPoints = new Point[points.length - 1];

        for (int j = 0; j < points.length; j++) {

            if (j < skipIndex) {
                sortPoints[j] = points[j];
            } else if (j > skipIndex) {

                sortPoints[j - 1] = points[j];
            }
        }
        Arrays.sort(sortPoints, points[skipIndex].SLOPE_ORDER);

        return sortPoints;
    }

    /**
     * Search the points.
     */
    private void search() {

        ArrayList<Point> tempPoints = new ArrayList<Point>();
        Point[] sortPoints;
        double currentSlope, lastSlope;
        int lastIndex;
        boolean isChanged;
        
        for (int i = 0; i < points.length; i++) {

            // get and sort points;
            sortPoints = getSortedPoints(i);
            lastIndex = sortPoints.length -1;
            currentSlope = Double.NaN;
            lastSlope = Double.NaN;
            tempPoints.clear();

            for (int k = 0; k < sortPoints.length; k++) {

                currentSlope = points[i].slopeTo(sortPoints[k]);
                isChanged = lastSlope != currentSlope;

                if (!isChanged) {
                    tempPoints.add(sortPoints[k]);
                }
                
                if ((isChanged || k == lastIndex && !isChanged) && tempPoints.size() >= SET_SIZE - 1) {

                    tempPoints.add(points[i]);
                    Point[] foundPoints = tempPoints.toArray(
                    	new Point[tempPoints.size()]
                    );
                    printTupleConditionally(foundPoints);
                }

                if (isChanged) {
                    tempPoints.clear();
                    tempPoints.add(sortPoints[k]);
                }
                

                lastSlope = currentSlope;
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
     * Main.
     * @param args the arguments.
     */
    public static void main(String[] args) {

        Fast fast = new Fast();
        fast.init(args[0]);
        fast.search();
        fast.show();
    }
}