package structures;

import java.util.Comparator;

import edu.princeton.cs.introcs.StdDraw;

/**
 * Represents a point in the plane
 */
public class Point implements Comparable<Point> {

    /**
     * Slope order comparator.
     */
    public final Comparator<Point> SLOPE_ORDER = new ByPoint();

    /**
     * The x coordinate.
     */
    private int x;

    /**
     * The y coordinate.
     */
    private int y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Draw this point
     */
    public void draw() {

        StdDraw.point(x, y);
    }
    
    /**
     * Draw the line segment from this point to that point.
     * @param that a 2nd point
     */
    public void drawTo(Point that) {

        StdDraw.line(x, y, that.x, that.y);
    }
    
    /**
     * String representation of the object.
     * @return String the string representation.
     */
    public String toString() {

        return String.format("(%d, %d)", x, y);
    }
    
    /**
     * Is this point lexicographically smaller than that point?
     * @param that a point to compare to
     * @return integer
     */
    public int compareTo(Point that) {

        if (y < that.y || y == that.y && x < that.x) {
            return -1;
        } else if (y == that.y && x == that.x) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * The slope between this point and that point.
     * @param that the other point
     * @return double
     */
    public double slopeTo(Point that) {

        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else if (y == that.y) {
            return 0d;
        } else {
            return ((double) that.y - y) / (that.x - x);
        }
    }

    /**
     * Compares the 2 points by the reference.
     */
    private class ByPoint implements Comparator<Point> {

        /**
         * Compares the points.
         * @param p1 point 1
         * @param p2 point 2
         */
        public int compare(Point p1, Point p2) {

            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
            if (p1Slope < p2Slope) {
                return -1;
            } else if (p1Slope > p2Slope) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Main
     * @param args the command line args
     */
    public static void main(String[] args) {

        System.out.println("Start");

        Point foo = new Point(1, 1);
        Point foo2 = new Point(1, 1);
        Point zoo = new Point(2, 2);
        Point loo = new Point(3, 3);
        Point coo = new Point(4, 3);
        Point roo = new Point(3, 4);
        Point yoo = new Point(3, 2);

        assert 0 == foo.SLOPE_ORDER.compare(zoo, loo);
        assert -1 == foo.SLOPE_ORDER.compare(zoo, roo);
        assert 1 == foo.SLOPE_ORDER.compare(zoo, yoo);
        assert 0 == foo.compareTo(foo2);
        assert -1 == foo.compareTo(zoo);
        assert -1 == loo.compareTo(coo);
        assert 1 == roo.compareTo(zoo);
        assert "(3, 3)".equals(loo.toString());

        System.out.println("End");
    }
}
