import edu.princeton.cs.algs4.Picture;


import java.awt.*;

public class SeamCarver {
    private Picture picture;
    double[][] computedEnergy;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.height = picture.height();
        this.width = picture.width();
        this.computedEnergy = new double[width][height];
        computeEnergy(computedEnergy);
    }

    private void computeEnergy(double[][] computedEnergy) {
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j ++) {
                double diffX = findDiff(i, j, 1, 0);
                double diffY = findDiff(i, j,0, 1);
                double finalDiff = diffX + diffY;
                computedEnergy[j][i] = finalDiff;
            }
        }
    }

    private double findDiff(int i, int j, int x, int y) {
        Color positiveColor = picture.get(Math.floorMod(j + x, width),  Math.floorMod(i - y, height));
        Color negativeColor = picture.get(Math.floorMod(j - x, width), Math.floorMod(i + y, height));
        double diffRed = Math.abs(positiveColor.getRed() - negativeColor.getRed());
        double diffGreen = Math.abs(positiveColor.getGreen() - negativeColor.getGreen());
        double diffBlue = Math.abs(positiveColor.getBlue() - negativeColor.getBlue());
        double finalDiff = (diffRed * diffRed) + (diffGreen * diffGreen) + (diffBlue * diffBlue);
        return finalDiff;

    }



    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return computedEnergy[x][y];
    }


    public int[] findHorizontalSeam() {
        transpose();
        int[] result = findVerticalSeam();
        transpose();
        return result; // sequence of indices for horizontal seam

    }

    private void transpose() {
        Picture temp = new Picture(height, width);
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                temp.set(i, j, picture.get(j, i));
            }
        }
        picture = temp;
        int t = width;
        width = height;
        height = t;
        double[][] helperComputedEnergy = new double[width][height];
        computeEnergy(helperComputedEnergy);
        this.computedEnergy = helperComputedEnergy;
    }



    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] dp = new double[width][height];
        int best = Integer.MAX_VALUE;
        int bestIndex = 0;
        for (int i = 0; i < height; i++) {
            // i is col
            for (int j = 0; j < width; j ++) {
                // j is row
                if (i == 0) {
                    dp[j][i] = computedEnergy[j][i];
                } else if (j == 0){
                    dp[j][i] =  (computedEnergy[j][i] + Math.min(dp[j][i - 1], dp[j + 1][i - 1]));
                } else if (j == width - 1) {
                    dp[j][i] =  (computedEnergy[j][i] + Math.min(dp[j][i - 1], dp[j - 1][i - 1]));
                } else {
                    dp[j][i] =  (computedEnergy[j][i] + Math.min(Math.min(dp[j][i - 1], dp[j + 1][i - 1]), dp[j - 1][i - 1]));
                }

                if (i == height - 1) {
                    if (dp[j][i] < best) {
                        best = (int) dp[j][i];
                        bestIndex = j;
                    }
                }
            }
        }
        return calculateBestPath(dp, bestIndex);

    }

    private int[] calculateBestPath(double[][] dp, int bestIndex) {
        // i is col
        int[] path = new int[height];
        for (int i = height - 1; i >= 0; i --) {
            if (i == height - 1) {
                path[i] = bestIndex;
                continue;
            } else {
                double best = Integer.MAX_VALUE;
                int helperBestIndex = bestIndex;
                for (int j = helperBestIndex - 1; j <= helperBestIndex + 1; j++) { // j is row
                    if (j >= 0 && j < width) {
                        if (dp[j][i] < best) {
                            best = dp[j][i];
                            bestIndex = j;
                        }
                    }
                }
                path[i] = bestIndex;
                helperBestIndex = bestIndex;
            }
        }
        return path;

    }


    public void removeHorizontalSeam(int[] seam) {// remove horizontal seam from picture
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from picture
        SeamRemover.removeVerticalSeam(picture, seam);
    }


}
