package structures;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;

/**
 * Kd Tree.
 */
public class KdTree {

    /**
     * The root node.
     */
    private Node root;

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
        root = insert(root, new Node(p));
        root.isYvector = true;
    }
    
    private Node insert (Node parent, Node child) {

        if (null == parent) {
            N++;
            return child;
        }

        int comp = parent.compare(child);
        if (comp == -1) {
            
            parent.left = insert(parent.left, child);
            parent.setLeftOrientation();
        } else if (comp == 1) {
            
            parent.right = insert(parent.right, child);
            parent.setRightOrientation();
        } else {
            
            if (child.equals(parent)) {
                if (!parent.equals(root)) return null;
            } else {
                parent.right = insert(parent.right, child);
                parent.setRightOrientation();
            }
        }
        return parent;
    }

    /**
     * does the set contain the point p?
     * @param p a point.
     * @return boolean
     */
    public boolean contains(Point2D p) {

        return (get(p) != null);
    }
    

    // value associated with the given key in subtree rooted at x; null if no such key
    private Point2D get(Point2D p) {
        
        Node x = root;
        Node find = new Node(p);
        
        while (x != null) {

            int cmp = x.compare(find);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else if (x.equals(find)) return x.point;
            else x = x.right;
        }
        return null;
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
        
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        
        keys(root, list, rect);
        return list;
    }

    /**
     * Recurse the tree and add contained points to the list.
     * @param n
     * @param list
     * @param rect
     */
    private void keys(Node n, ArrayList<Point2D> list, RectHV rect) {

        if (n == null) return;

        int cmp = n.compare(rect);  
        if (cmp < 0) keys(n.left, list, rect); 
        else if (cmp > 0) keys(n.right, list, rect);
        else {
            if (rect.contains(n.point)) list.add(n.point);
            keys(n.left, list, rect);
            keys(n.right, list, rect);
        }
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
         * Does point denote a y-vector?
         */
        public boolean isYvector = false;

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
        
        /**
         * Compare 2 this Node and another.
         * @param n
         * @return
         */
        public int compare(Node n) {
            
            if (isYvector) {
                if (point.x() > n.point.x()) return -1;
                if (point.x() < n.point.x()) return 1;
                else return 0;
            } else {
                if (point.y() > n.point.y()) return -1;
                if (point.y() < n.point.y()) return 1;
                else return 0;
            }
        }
        
        /**
         * Compare this node with the rectangle.
         * @param rect
         * @return
         */
        public int compare(RectHV rect) {
            
            if (isYvector) {
                if (point.x() > rect.xmax()) return -1;
                if (point.x() < rect.xmin()) return 1;
                else return 0;
            } else {
                if (point.y() > rect.ymax()) return -1;
                if (point.y() < rect.ymin()) return 1;
                else return 0;
            }
        }
        
        /**
         * Set the orientation of the right Node.
         */
        public void setRightOrientation() {
            right.isYvector = isYvector ? false : true; 
        }
        
        /**
         * Set the orientation of the Left Node.
         */
        public void setLeftOrientation() {
            left.isYvector = isYvector ? false : true; 
        }
    }

    /**
     * main.
     * @param args the arguments
     */
    public static void main(String[] args) {

        KdTree kd = new KdTree();
        
        Point2D point1 = new Point2D(0.5, 0.5);
        kd.insert(point1);
        assert kd.size() == 1;
        assert kd.contains(point1);
        assert false == kd.contains(new Point2D(0.7, 0.7));
        
        Point2D point2 = new Point2D(0.4, 0.4);
        kd.insert(point2);
        assert kd.size() == 2;
        assert kd.contains(point2);
        
        Point2D point3 = new Point2D(0.5, 0.5);
        kd.insert(point3);
        assert kd.size() == 2;
        assert kd.contains(point3);
        
        Point2D point4 = new Point2D(0.5, 0.4);
        kd.insert(point4);
        assert kd.size() == 3;
        assert kd.contains(point4);
        
        Point2D point5 = new Point2D(0.6, 0.5);
        kd.insert(point5);
        assert kd.size() == 4;
        assert kd.contains(point5);
        assert false == kd.contains(new Point2D(0.6, 0.4));
        assert false == kd.contains(new Point2D(0.6, 0.3));
        
        //RectHV rect = new RectHV(0.55, 0.35, 0.65, 0.45);
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        for (Point2D thePoint : kd.range(rect)) {
        	System.out.println(thePoint.toString());
        }
        
        System.out.println();
        RectHV rect1 = new RectHV(0.45, 0.35, 0.55, 0.55);
        for (Point2D thePoint : kd.range(rect1)) {
        	System.out.println(thePoint.toString());
        }
        
        System.out.println();
        RectHV rect2 = new RectHV(0.35, 0.35, 0.55, 0.45);
        for (Point2D thePoint : kd.range(rect2)) {
        	System.out.println(thePoint.toString());
        }
        
        System.out.println();
        RectHV rect3 = new RectHV(0.55, 0.45, 0.6, 0.5);
        for (Point2D thePoint : kd.range(rect3)) {
        	System.out.println(thePoint.toString());
        }
    }
}
