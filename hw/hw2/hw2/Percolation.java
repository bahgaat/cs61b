package hw2;

import edu.princeton.cs.algs4.QuickFindUF;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashMap;
import java.util.Map;



public class Percolation {
    /* all methods must take constant time but the constructor should be N ^ 2. */
    private final int closed = 0;
    private final int opened = 1;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUF2;
    private int N;
    private int openStates;
    private Map<String, Integer> mapPositionToMap = new HashMap<>(N * N);
    private boolean percolates = false;


    //TODO any time you open, Connect with all neighbbors.
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N must be more than 0");
        }
        this.N = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N);
        weightedQuickUnionUF2 = new WeightedQuickUnionUF(N * N);
        int i = 0;
        String position;
        for (int row = 0; row < N; row += 1) {
            for (int col = 0; col < N; col += 1) {
                position = row + "row" + col + "col";
                if (row == 0) {
                    weightedQuickUnionUF.union(0, i);
                    weightedQuickUnionUF2.union(0, i);
                } else if (row == N - 1) {
                    weightedQuickUnionUF.union((N * N - 1), i);
                }
                mapPositionToMap.put(position, i * 10 + closed);
                i += 1;
            }
        }
    }


    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkExceptions(row, col);
        String keyOfTheMap = null;
        int allNumber;
        int number;
        int allNumberNeighbor;
        int numberNeighbor;
        int stateNeighbor;

        int oldRow = row;
        int oldCol = col;
        int newRow = row;
        int newCol = col;
        if (!isOpen(row, col)) {
            openStates += 1;
            keyOfTheMap = oldRow + "row" + oldCol + "col";
            allNumber = mapPositionToMap.get(keyOfTheMap);
            allNumber = allNumber / 10;
            number = allNumber;
            allNumber =  allNumber * 10 + opened;
            mapPositionToMap.replace(keyOfTheMap, allNumber);


            /* connect it with its neighbors. */
            int i = 0;
            while (4 > i) {
                if (i == 0 && oldRow != N - 1) {
                    newRow += 1;
                } else if (i == 1 && oldRow != 0) {
                    newRow -= 1;
                } else if (i == 2 && oldCol != N - 1) {
                    newCol += 1;
                } else if (i == 3 && oldCol != 0) {
                    newCol -= 1;
                }

                allNumberNeighbor = mapPositionToMap.get(newRow + "row" + newCol + "col");
                numberNeighbor = allNumberNeighbor / 10;
                stateNeighbor = allNumberNeighbor % 10;
                if (stateNeighbor == opened) {
                    weightedQuickUnionUF.union(number, numberNeighbor);
                    weightedQuickUnionUF2.union(number, numberNeighbor);
                }

                newRow = oldRow;
                newCol = oldCol;
                i += 1;
            }


            boolean percolates2 = weightedQuickUnionUF.connected(0, (N * N - 1));
            if (percolates2) {
                percolates = true;
            }
        }
    }



    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        checkExceptions(row, col);
        int allNumber = mapPositionToMap.get(row + "row" + col + "col");
        int state = allNumber % 10;
        return state == opened;
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        //TODO if this row and col is connected with any above number.eg 1, 2,3 ,4 return true, else false.
        checkExceptions(row, col);
        int allNumber = mapPositionToMap.get(row + "row" + col + "col");
        int number = allNumber / 10;
        int state = allNumber % 10;
        boolean isFull = weightedQuickUnionUF2.connected(number, 0) && state == opened;
        return isFull;
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openStates;
    }

    public boolean percolates() {
        // does the system percolate?
        return percolates;
    }

    private void checkExceptions(int row, int column) {
        if (row < 0 || row > N - 1) {
            throw new IndexOutOfBoundsException("row must be in the range");
        } else if (column < 0 || column > N - 1) {
            throw new IndexOutOfBoundsException("column must be in the range");
        }
    }

   @Test
    public static void main(String[] args) {
        // use for unit testing (not required)
        Percolation percolationSystemNotPercolates = new Percolation(4);
        percolationSystemNotPercolates.open(1,2);
        percolationSystemNotPercolates.open(2, 2);
        assertTrue(percolationSystemNotPercolates.isOpen(1, 2));
        assertTrue(percolationSystemNotPercolates.isOpen(2, 2));
        assertFalse(percolationSystemNotPercolates.isFull(1, 2));
        assertFalse(percolationSystemNotPercolates.isFull(2, 2));
        assertEquals(percolationSystemNotPercolates.numberOfOpenSites(), 2);
        percolationSystemNotPercolates.open(2, 1);
        assertFalse(percolationSystemNotPercolates.percolates());
        Percolation percolationSystemThatPercolates = new Percolation(4);
        percolationSystemThatPercolates.open(1, 1);
        percolationSystemThatPercolates.open(0, 1);
        percolationSystemThatPercolates.open(1, 2);
        assertFalse(percolationSystemThatPercolates.isFull(0, 3));
        assertFalse(percolationSystemThatPercolates.isFull(0, 2));
        assertFalse(percolationSystemThatPercolates.isFull(0, 0));
        assertFalse(percolationSystemThatPercolates.isFull(1, 0));
        assertFalse(percolationSystemThatPercolates.isFull(2, 0));
        assertFalse(percolationSystemThatPercolates.isFull(3, 0));
        assertFalse(percolationSystemThatPercolates.isFull(3, 1));

    }


}
