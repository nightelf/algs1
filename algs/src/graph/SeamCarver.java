package graph;

import java.awt.Color;

import edu.princeton.cs.introcs.Picture;

/**
 * Seam carver class
 */
public class SeamCarver {
    
    /**
     * The edge energy constant.
     */
    final private static double EDGE_ENERGY =  195075.0;
    
    /**
     * The energy array.
     */
    private Pixel[][] pixels;
    
    /**
     * Constructor.
     * @param picture
     */
    public SeamCarver(Picture picture) {
        
        int width = picture.width();
        int height = picture.height();
        pixels = new Pixel[height][width];
        
        // fill energy grid
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new Pixel(picture.get(j, i), energy(j, i));
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
     * @param p1 Pixel 1 color.
     * @param p2 Pixel 2 color.
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
        
        // initialize vars
        int width = pixels[0].length;
        int height = pixels.length;
        int heightIndex = height - 1;
        int start, end;
        double cumEnergy;
        DirectedRowEdge[][] de = new DirectedRowEdge[height][width];
        
        // initialize 1st row
        for (int i = 0; i < width; i++)
            de[0][i] = new DirectedRowEdge(i, pixels[0][i].energy);
        
        // calculate shortest path
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                start = (x != 0) ? x - 1 : x;
                end = (x != width - 1) ? x + 1 : x;
                for (int i = start; i < end; i++) {
                    de[y][x] = new DirectedRowEdge(i, Double.POSITIVE_INFINITY);
                    cumEnergy = de[y - 1][i].cumEnergy + pixels[y][x].energy;
                    if (cumEnergy < de[y][x].cumEnergy) {
                        de[y][x].cumEnergy = cumEnergy;
                        de[y][x].from = i;
                    }
                }
            }
        }
        
        // find lowest energy endpoint for the shortest path
        cumEnergy = Double.POSITIVE_INFINITY;
        int tempIndex = 0;
        for (int i = 0; i < width; i++) {
            if (de[heightIndex][i].cumEnergy < cumEnergy) {
                cumEnergy = de[heightIndex][i].cumEnergy;
                tempIndex = i;
            }
        }

        // retrace shortest path and build seam.
        int[]seam = new int[height];
        seam[heightIndex] = tempIndex;
        for (int i = heightIndex - 1; i >= 0; i--) {
            seam[i] = de[i + 1][seam[i + 1]].from;
        }
        
        return seam;
    }

    
    /**
     * remove horizontal seam from current picture.
     * @param a
     */
    public void removeHorizontalSeam(int[] a) {

        // remove Seam from energy matrix
        int newLength = pixels[0].length - 1;
        for (int i = 0; i < a.length; i++) {
            Pixel[] temp = new Pixel[newLength];
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
            Pixel[] temp = new Pixel[newLength];
            System.arraycopy(pixels[i], 0, temp, 0, a[i]);
            System.arraycopy(pixels[i], a[i] + 1, temp, a[i], newLength - a[i]);
            pixels[i] = temp;
        }
        
        // recalculate affected energies.
        int start, end;
        for (int y = 0; y < a.length; y++) {
            start = (a[y] != 0) ? a[y] - 1 : a[y];
            end = (a[y] != newLength - 1) ? a[y] + 1 : a[y];
            for (int i = start; i < end; i++) {
                pixels[y][i].energy = energy(y, i);
            }
        }
    }

    /**
     * Pixel contains color and energy information for a given pixel.
     */
    private class Pixel {
        
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
        public Pixel(Color color, double energy) {
            
            this.color = color;
            this.energy = energy;
        }
    }
    
    /**
     * Pixel contains color and energy information for a given Pixel.
     */
    private class DirectedRowEdge {
        
        /**
         * Representation of RGB color.
         */
        public int from;
        
        /**
         * The calculated energy.
         */
        public double cumEnergy;
        
        /**
         * Constructor.
         * @param color the color
         * @param energy the energy
         */
        public DirectedRowEdge(int from, double cumEnergy) {
            
            this.from = from;
            this.cumEnergy = cumEnergy;
        }
    }

    public static void main(String[] args) {
        
        
    }
}
