/**
 * 
 */
package clients;

import structures.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

/**
 * @author jared
 *
 */
public class Brute {

	private Brute(Point[] points) {
		
		
	}
	
    private void printTuple(Point[] points) {
        
        System.out.println(
            String.format(
                "%s -> %s -> %s -> %s",
                points[0].toString(),
                points[1].toString(),
                points[2].toString(),
                points[3].toString()
            )
        );
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
    	System.out.println("start");
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
            System.out.println(p);
        }

        // display to screen all at once
        StdDraw.show(0);
        System.out.println("end");
    }

}
