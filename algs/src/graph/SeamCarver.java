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
     * The energy array.
     */
    private pixel[][] pixels;
    
    /**
     * Constructor.
     * @param picture
     */
    public SeamCarver(Picture picture) {
        
        int width = picture.width();
        int height = picture.height();
        pixels = new pixel[height][width];
        
        // fill energy grid
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new pixel(picture.get(j, i), energy(j, i));
            }
        }
    }

    /**
     * current picture.
     * @return
     */
    public Picture picture() {
        
        int width = pixels[0].length;
        int height = pixels.length;
        
        Picture pic = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pic.set(j, i, pixels[i][j].color);
            }
        }
        
        return pic;
    }

    /**
     * width  of current picture.
     * @return
     */
    public int width() {
        
        return pixels[0].length;
    }

    /**
     * height of current picture.
     * @return
     */
    public int height() {
        
        return pixels.length;
    }

    /**
     * energy of pixel at column x and row y in current picture.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return
     */
    public double energy(int x, int y) {

        if (x == 0 || y == 0 || x == pixels[0].length || y == pixels.length) {
            return EDGE_ENERGY;
        }
        
        double horizontal = colorDiff(
            pixels[x - 1][y].color,
            pixels[x + 1][y].color
        );
        
        double vertical = colorDiff(
            pixels[x][y - 1].color,
            pixels[x][y + 1].color
        );

        return horizontal + vertical;
    }

    /**
     * Compute the color difference.
     * @param p1 pixel 1 color.
     * @param p2 pixel 2 color.
     * @return
     */
    private double colorDiff(Color p1, Color p2) {
        
        double red = p2.getRed() - p1.getRed();
        double green = p2.getGreen() - p1.getGreen();
        double blue = p2.getBlue() - p1.getBlue();
        return red * red + green * green + blue * blue;
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

        // remove Seam from energy matrix
        int newLength = pixels[0].length - 1;
        for (int i = 0; i < a.length; i++) {
            pixel[] temp = new pixel[newLength];
            System.arraycopy(pixels[a[i]], 0, temp, 0, i);
            System.arraycopy(pixels[a[i]], i + 1, temp, i, newLength - i);
            pixels[a[i]] = temp;
        }
    }

    /**
     * remove vertical seam from current picture.
     * @param a an array of indexes to delete.
     */
    public void removeVerticalSeam(int[] a) {
        
        // remove Seam from energy matrix
        int newLength = pixels[0].length - 1;
        for (int i = 0; i < a.length; i++) {
            pixel[] temp = new pixel[newLength];
            System.arraycopy(pixels[i], 0, temp, 0, a[i]);
            System.arraycopy(pixels[i], a[i] + 1, temp, a[i], newLength - a[i]);
            pixels[i] = temp;
        }
    }

    /**
     * Pixel contains color and energy information for a given pixel.
     */
    private class pixel {
        
        /**
         * Representation of RGB color.
         */
        public Color color;
        
        /**
         * The calculated energy.
         */
        public double energy;
        
        /**
         * Constructor.
         * @param color the color
         * @param energy the energy
         */
        public pixel(Color color, double energy) {
            
            this.color = color;
            this.energy = energy;
        }
    }
}
