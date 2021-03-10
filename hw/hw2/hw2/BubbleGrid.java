package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

//TODO the complexity of the algorithms is O(Q) * O(N) * O(N) * O(C) * O(N)
class BubbleGrid {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int[][] grid;
    private final int brick = 1;
    private final int noBrick = 0;
    private int rowsLength;
    private int columnLength;
    private int maxRow;
    private int maxCol;
    private int sizeOfUnion;

    public int[] hitBricks(int[][] gridValues, int[][] hits) {
        this.rowsLength = gridValues.length;
        this.columnLength = gridValues[0].length;
        this.grid = new int[rowsLength][columnLength];
        for (int row = 0; row < grid.length; row += 1) {
            for (int col = 0; col < grid[row].length; col += 1) {
               this.grid[row][col] = gridValues[row][col];
            }
        }
        this.sizeOfUnion = rowsLength * columnLength;
        initializeUnionDataStructure();
        this.maxRow = grid.length;
        this.maxCol = grid[0].length;
        seeIfEachPositionIsOpenedAndConnectToNeighbors();
        return eraseBricks(hits);
    }

    private void initializeUnionDataStructure() {
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(rowsLength * columnLength);
    }


    private void seeIfEachPositionIsOpenedAndConnectToNeighbors() {
        int numberInUnion = 0;
        for (int row = 0; row < grid.length; row += 1) {
            for (int col = 0; col < grid[row].length; col += 1) {
                if (grid[row][col] == brick) {
                    connectToNeighbors(row, col, maxRow, maxCol, numberInUnion);
                }
                numberInUnion += 1;
            }
        }
    }

    private void connectToNeighbors(int row, int col, int maxRow, int maxCol, int numberInUnion) {
        int i = 0;
        int neighborRow = row;
        int neighborCol = col;
        int numberNeighborInUnion = numberInUnion;
        while (3 > i) {
            if (i == 0 && row != maxRow - 1) {
                neighborRow += 1;
                numberNeighborInUnion += maxCol;
            } else if (i == 1 && col != maxCol - 1) {
                neighborCol += 1;
                numberNeighborInUnion += 1;
            } else if (i == 2 && col != 0) {
                neighborCol -= 1;
                numberNeighborInUnion -= 1;
            }

            if (grid[neighborRow][neighborCol] == brick) {
                if (isNeighborUnionNumberIsInBound(numberNeighborInUnion)) {
                    weightedQuickUnionUF.union(numberInUnion, numberNeighborInUnion);
                }
            }
            i += 1;
            neighborRow = row;
            neighborCol = col;
            numberNeighborInUnion = numberInUnion;
        }
    }

    private boolean isNeighborUnionNumberIsInBound(int numberNeighborInUnion) {
        return numberNeighborInUnion >= 0 && numberNeighborInUnion < sizeOfUnion;
    }

    private int[] eraseBricks(int[][] hits) {
        int[] arrayOfDroppedBricks = new int[hits.length];
        for (int i = 0; i < hits.length; i += 1) {
            int row = hits[i][0];
            int col = hits[i][1];
            grid[row][col] = noBrick;
            initializeUnionDataStructure(); /* O(N) */
            seeIfEachPositionIsOpenedAndConnectToNeighbors(); /* O(N) */
            int numberInUnion = row * maxCol + col;
            ArrayList<Integer> arrayListOfNeighbors = new ArrayList<Integer>();


            if (col != maxCol - 1) {
                int numberInUnionInRightNeighbor = numberInUnion + 1;
                if (isNeighborUnionNumberIsInBound(numberInUnionInRightNeighbor)) {
                    numberInUnionInRightNeighbor = numberInUnionInRightNeighbor;
                }
                arrayListOfNeighbors.add(numberInUnionInRightNeighbor);
            }

            if (col != 0) {
                int numberInUnionInLeftNeighbor = numberInUnion - 1;
                if (isNeighborUnionNumberIsInBound(numberInUnionInLeftNeighbor)) {
                    numberInUnionInLeftNeighbor = numberInUnionInLeftNeighbor;
                }
                arrayListOfNeighbors.add(numberInUnionInLeftNeighbor);
            }

            if (row != maxRow - 1) {
                int numberInUnionInBelowNeighbor = (row + 1) * maxCol + col;
                if (isNeighborUnionNumberIsInBound(numberInUnionInBelowNeighbor)) {
                    numberInUnionInBelowNeighbor = numberInUnionInBelowNeighbor;
                }
                arrayListOfNeighbors.add(numberInUnionInBelowNeighbor);
            }

            int numberOfDroppedBricks = 0;
            for (int j = 0; j < arrayListOfNeighbors.size(); j += 1) {
                numberOfDroppedBricks += dropNeighborsIfPossible(arrayListOfNeighbors.get(j)); /* O(C) * O(N) */
            }
            arrayOfDroppedBricks[i] = numberOfDroppedBricks;
        }
        return arrayOfDroppedBricks;
    }

    private int dropNeighborsIfPossible(int numberInUnionInNeighbor) {
        boolean connectedToAbove = false;
        int numberOfDroppedBricks = 0;
        for (int j = 0; j < columnLength; j += 1) {
            if (weightedQuickUnionUF.connected(numberInUnionInNeighbor, j)) {
                connectedToAbove = true;
            }
        }
        if (!connectedToAbove) {
            numberOfDroppedBricks += dropBricks(numberInUnionInNeighbor);
        }
        return numberOfDroppedBricks;
    }

    private int dropBricks(int numberInUnionInNeighbor) {
        int numberOfDroppedBricks = 0;
        int i = 0;
        for (int row = 0; row < grid.length; row += 1) {
            for (int col = 0; col < grid[row].length; col += 1) {
                if (weightedQuickUnionUF.connected(i, numberInUnionInNeighbor) && grid[row][col] == brick) {
                    grid[row][col] = noBrick;
                    numberOfDroppedBricks += 1;
                }
                i += 1;
            }
        }
        return numberOfDroppedBricks;
    }



    @Test
    public static void main(String[] args) {
        BubbleGrid bubbleGrid = new BubbleGrid();

        int[][] grid = {{1, 0, 0, 0},
                {1, 1, 1, 0}};
        int[][] hits = {{1, 0}};
        int[] expected = {2};
        int[] answer = bubbleGrid.hitBricks(grid, hits);
        assertArrayEquals(expected, answer);

        int[][] grid2 = {{1, 1, 0},
                {1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int[][] darts2 = {{2, 2}, {2, 0}};
        int[] expected2 = {0, 4};
        int[] answer2 = bubbleGrid.hitBricks(grid2, darts2);
        assertArrayEquals(expected2, answer2);

        int[][] grid3 = {{1, 0, 1}, {1, 1, 1}};
        int[][] darts3 = {{0, 0}, {0, 2}, {1, 1}};
        int[] expected3 = {0, 3, 0};
        int[] answer3 = bubbleGrid.hitBricks(grid3, darts3);
        assertArrayEquals(expected3, answer3);

        int[][] grid4 = {{1}, {1}, {1}, {1}, {1}};
        int[][] darts4 = {{3, 0}, {4, 0}, {1, 0}, {2, 0}, {0, 0}};
        int[] expected4 = {1, 0, 1, 0, 0};
        int[] answer4 = bubbleGrid.hitBricks(grid4, darts4);
        assertArrayEquals(expected4, answer4);

        int[][] grid5 = {{0, 1, 1, 1, 1}, {1, 1, 1, 1, 0}, {1, 1, 1, 1, 0},
                {0, 0, 1, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        int[][] darts5 = {{6, 0}, {1, 0}, {4, 3}, {1, 2}, {7, 1}, {6, 3}, {5, 2}, {5, 1},
                {2, 4}, {4, 4}, {7, 3}};
        int[] expected5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] answer5 = bubbleGrid.hitBricks(grid5, darts5);
        assertArrayEquals(expected5, answer5);

    }



}

