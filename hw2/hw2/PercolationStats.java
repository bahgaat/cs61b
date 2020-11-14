package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import org.junit.Test;


public class PercolationStats {
    private int T;
    private double arrayOfThresholds[];


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be more than 0");
        }
        this.T = T;
        this.arrayOfThresholds = new double[T];
        performExperiment(N, pf);
    }

    private void performExperiment(int N, PercolationFactory pf) {
        int randomRow;
        int randomColumn;
        int openSites = 0;
        float percolationThreshold;
        Percolation percolation = pf.make(N);
        int arrayIndex = 0;
        while (T > 0) {
            while(!percolation.percolates()) {
                randomRow = StdRandom.uniform( N - 1);
                randomColumn = StdRandom.uniform( N - 1);
                if (!percolation.isOpen(randomRow, randomColumn)) {
                    percolation.open(randomRow, randomColumn);
                    openSites += 1;
                }
                if (openSites == N * N) {
                    break;
                }

            }
            percolationThreshold = openSites / N * N;
            arrayOfThresholds[arrayIndex] = percolationThreshold;
            arrayIndex += 1;
            openSites = 0;
            T -= 1;
        }
    }



    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arrayOfThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrayOfThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double confidenceLow = mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
        return confidenceLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double confidenceHigh = mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
        return confidenceHigh;
    }
    /*
    @Test
    public static void main(String[] args) {
        PercolationFactory percolationFactory = new PercolationFactory();
        PercolationStats percolationStats = new PercolationStats(5, 5, percolationFactory);
    }

     */



}
