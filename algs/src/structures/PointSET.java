package structures;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;

/**
 * Point Set.
 */
public class PointSET {

    /**
     * construct an empty set of points.
     */
    public PointSET() {

    }

    /**
     * is the set empty.
     * @return
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * number of points in the set.
     * @return
     */
    public int size() {
        return 1;
    }

    /**
     * add the point p to the set (if it is not already in the set).
     * @param p
     */
    public void insert(Point2D p) {

    }

    /**
     * does the set contain the point p?
     * TODO incomplete.
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return true;
    }

    /**
     * draw all of the points to standard draw.
     * TODO incomplete.
     */
    public void draw() {

    }

    /**
     * all points in the set that are inside the rectangle.
     * TODO incomplete.
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {

        return new ArrayList<Point2D>();
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty.
     * TODO incomplete.
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        return p;
    }
}

