package graph;

import java.awt.Color;

import edu.princeton.cs.introcs.Picture;

/**
 * Created by jared on 4/2/14.
 */
public class SeamCarver {
    
    /**
     * The edge energy constant.
     */
    final private static double EDGE_ENERGY =  195075.0;
    
    /**
     * The picture.
     */
    private Picture pic;
    
    /**
     * The energy array.
     */
    private double[][] energy;
    
    /**
     * Constructor.
     * @param picture
     */
    public SeamCarver(Picture picture) {
        
        pic = new Picture(picture);
        int width = pic.width();
        int height = pic.height();
        energy = new double[height][width];
        
        // fill energy grid
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energy[i][j] = energy(j, i);
            }
        }
    }

    /**
     * current picture.
     * @return
     */
    public Picture picture() {
        
        return pic;
    }

    /**
     * width  of current picture.
     * @return
     */
    public int width() {
        
        return pic.width();
    }

    /**
     * height of current picture.
     * @return
     */
    public int height() {
        
        return pic.height();
    }

    /**
     * energy of pixel at column x and row y in current picture.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return
     */
    public double energy(int x, int y) {

        if (x == 0 || y == 0 || x == energy[0].length || y == energy.length) {
            return EDGE_ENERGY;
        }
        
        double horizontal = colorDiff(pic.get(x - 1, y), pic.get(x + 1, y));
        double vertical = colorDiff(pic.get(x, y - 1), pic.get(x, y + 1));
        return horizontal + vertical;
    }

    /**
     * Compute the color difference.
     * @param p1 pixel 1 color.
     * @param p2 pixel 2 color.
     * @return
     */
    private double colorDiff(Color p1, Color p2) {
        
        double red = Math.pow(p2.getRed() - p1.getRed(), 2);
        double green = Math.pow(p2.getGreen() - p1.getGreen(), 2);
        double blue = Math.pow(p2.getBlue() - p1.getBlue(), 2);
        return red + green + blue;
    }
    
    /**
     * sequence of indices for horizontal seam in current picture
     * @return
     */
    public int[] findHorizontalSeam() {
        
        // TODO finish
        return new int[5];
    }

    /**
     * sequence of indices for vertical   seam in current picture.
     * @return
     */
    public int[] findVerticalSeam() {
        
        // TODO finish
        return new int[5];
    }

    /**
     * remove horizontal seam from current picture.
     * @param a
     */
    public void removeHorizontalSeam(int[] a) {
        
        //TODO finish
    }

    /**
     * remove vertical seam from current picture.
     * @param a
     */
    public void removeVerticalSeam(int[] a) {
        
        //TODO finish
        for (int i = 0; i < a.length; i++) {
            //System.arraycopy(arg0, arg1, arg2, arg3, arg4);
        }
    }
}
