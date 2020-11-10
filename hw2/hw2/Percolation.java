package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class Percolation {
    /* all methods must take constant time but the constructor should be N ^ 2. */
    private final String emptyClosed = "emptyClosed";
    private final String emptyOpened = "emptyOpened";
    private final String fullClosed = "fullClosed";
    private final String fullOpened = "fullOpened";
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private Map<String, Map<Integer, String>> mapPositionToMap = new HashMap<String, Map<Integer, String>>();
    private int N;
    private int openStates;


    //TODO any time you open, Connect with all neighbbors.
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N must be more than 0");
        }
        this.N = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N);
        int i = 0;
        String position;
        Map<Integer, String> mapParentToState;
        for (int row = 0; row < N; row += 1) {
            for (int col = 0; col < N; col += 1) {
                position = row + "row" + col + "col";
                mapParentToState = new HashMap<>();
                if (row == 0) {
                    weightedQuickUnionUF.union(0, i);
                    mapParentToState.put(i, fullClosed);
                } else if (row == N - 1) {
                    weightedQuickUnionUF.union(N * N - 1, i);
                    mapParentToState.put(i, emptyClosed);
                } else {
                    mapParentToState.put(i, emptyClosed);
                }
                mapPositionToMap.put(position, mapParentToState);
                i += 1;
            }
        }
    }


    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkExceptions(row, col);
        openStates += 1;
        Map<Integer, String> mapParentToState;
        Map<Integer, String> mapParentToStateNeighbor;
        int keyOfTheMainPosition = 0;
        int oldRow = row;
        int oldCol = col;
        int newRow = row;
        int newCol = col;
        boolean isConnectedToFull = false;
        if (!isOpen(row, col)) {
            mapParentToState = mapPositionToMap.get(oldRow + "row" + oldCol + "col");
            for (Map.Entry<Integer, String> set : mapParentToState.entrySet()) {
                keyOfTheMainPosition = set.getKey();
                if (set.getValue().equals(emptyClosed)) {
                    mapParentToState.replace(keyOfTheMainPosition, emptyOpened);
                } else if (set.getValue().equals(fullClosed)) {
                    mapParentToState.replace(keyOfTheMainPosition, fullOpened);
                }
            }

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

                mapParentToStateNeighbor = mapPositionToMap.get(newRow + "row" + newCol + "col");
                for (Map.Entry<Integer, String> set : mapParentToStateNeighbor.entrySet()) {
                    String state = set.getValue();
                    if (state.equals(emptyOpened)) {
                        weightedQuickUnionUF.union(keyOfTheMainPosition, set.getKey());
                    } else if (state.equals(fullOpened)) {
                        weightedQuickUnionUF.union(keyOfTheMainPosition, set.getKey());
                        isConnectedToFull = true;
                    }
                }
                newRow = oldRow;
                newCol = oldCol;
                i += 1;
            }
            if (isConnectedToFull) {
                mapParentToState.replace(keyOfTheMainPosition, fullOpened);
            }
        }
    }



    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        checkExceptions(row, col);
        Map<Integer, String>  mapParentToState = mapPositionToMap.get(row + "row" + col + "col");
        String state = null;
        for (Map.Entry<Integer, String> set : mapParentToState.entrySet()) {
            state = set.getValue();
        }
        return state.equals(emptyOpened) || state.equals(fullOpened);
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        //TODO if this row and col is connected with any above number.eg 1, 2,3 ,4 return true, else false.
        checkExceptions(row, col);
        int mainParent = 0;
        String state = null;
        Map<Integer, String>  mapMainParentToState = mapPositionToMap.get(row + "row" + col + "col");
        for (Map.Entry<Integer, String> set : mapMainParentToState.entrySet()) {
            mainParent = set.getKey();
            state = set.getValue();
        }
        boolean isFull = weightedQuickUnionUF.connected(mainParent, 0) &&  (!state.equals(fullClosed));
        return isFull;

    }

    public int numberOfOpenSites() {
        // number of open sites
        return openStates;
    }

    public boolean percolates() {
        // does the system percolate?
        return weightedQuickUnionUF.connected(0, N * N - 1);
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
