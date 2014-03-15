package structures;

import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Kd Tree.
 */
public class KdTree {

    /**
     * For vertical comparator.
     */
	final public static Comparator<Node> FOR_VERTICAL = new ForVertical();

    /**
     * For horizontal comparator.
     */
	final public static Comparator<Node> FOR_HORIZONTAL = new ForHorizontal();

    /**
     * The root node.
     */
    private Node root;

    /**
     * The rectangle.
     */
    private RectHV rect;

    /**
     * The size of the KdTree
     */
    private int N = 0;



    /**
     * construct an empty set of points.
     */
    public KdTree() {

    }

    /**
     * is the set empty.
     * @return
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * number of points in the set.
     * @return
     */
    public int size() {

        return N;
    }

    /**
     * add the point p to the set (if it is not already in the set).
     * @param p a point
     */
    public void insert(Point2D p) {
        
        if (null == p) return;
        root = insert(root, new Node(p), FOR_VERTICAL);
    }
    
    private Node insert (Node parent, Node child, Comparator<Node> c) {

        if (null == parent) {
            N++;
            return child;
        }

        int comp = c.compare(child, parent);
        if (comp == -1) {
            
            parent.left = insert(parent.left, child, getNext(c));
        } else if (comp == 1) {
            
            parent.right = insert(parent.right, child, getNext(c));
        } else {
            
            if (child.equals(parent)) {
                if (!parent.equals(root)) return null;
            } else {
                parent.right = insert(parent.right, child, getNext(c));
            }
        }
        return parent;
    }

    /**
     * Gets the Next comparator.
     * @param c the comparator.
     * @return Comparator<Node>
     */
    private Comparator<Node> getNext(Comparator<Node> c) {

        if (c.getClass() == FOR_VERTICAL.getClass()) {
            return FOR_HORIZONTAL;
        } else {
            return FOR_VERTICAL;
        }
    }

    /**
     * does the set contain the point p?
     * @param p a point.
     * @return boolean
     */
    public boolean contains(Point2D p) {

        return true;
    }

    /**
     * draw all of the points to standard draw.
     */
    public void draw() {

    }

    /**
     * all points in the set that are inside the rectangle.
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        
        return new ArrayList<Point2D>();
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty.
     * @param p point
     * @return
     */
    public Point2D nearest(Point2D p) {
        return p; // TODO implement
    }

    /**
     * Node.
     */
    private class Node {

        /**
         * The point.
         */
        public Point2D point;

        /**
         * The left/bottom subtree.
         */
        public Node left;

        /**
         * The right/top subtree.
         */
        public Node right;

        /**
         * Constructor.
         * @param point point.
         * @param rect rectangle.
         */
        public Node(Point2D point) {

            this.point = point;
        }

        /**
         * Is this Node equivalent the to passed node?
         * @param n node
         * @return
         */
        public boolean equals(Node n) {

            return point.x() == n.point.x() && point.y() == n.point.y();
        }
    }

    /**
     * Compares the nodes vertically.
     */
    private static class ForVertical implements Comparator<Node> {

        /**
         * Compares the Nodes.
         * @param n1 node 1
         * @param n2 node 2
         */
        public int compare(Node n1, Node n2) {

            if (n1.point.x() < n2.point.x()) return -1;
            else if (n1.point.x() > n2.point.x()) return 1;
            else return 0;
        }
    }

    /**
     * Compares the nodes horizontally.
     */
    private static class ForHorizontal implements Comparator<Node> {

        /**
         * Compares the Nodes.
         * @param n1 node 1
         * @param n2 node 2
         */
        public int compare(Node n1, Node n2) {

            if (n1.point.y() < n2.point.y()) return -1;
            else if (n1.point.y() > n2.point.y()) return 1;
            else return 0;
        }
    }

    /**
     * main.
     * @param args the arguments
     */
    public static void main(String[] args) {

        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.5, 0.5));
        assert kd.size() == 1;
        kd.insert(new Point2D(0.4, 0.4));
        assert kd.size() == 2;
        kd.insert(new Point2D(0.5, 0.5));
        assert kd.size() == 2;
        kd.insert(new Point2D(0.5, 0.4));
        assert kd.size() == 3;
        kd.insert(new Point2D(0.6, 0.5));
        assert kd.size() == 4;
    }
}
