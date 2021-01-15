package hw4.puzzle;
import java.util.*;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState  {
    HashMap<Integer, int[]> goalHashMap = new HashMap<>();
    HashMap<Integer, int[]> actualHashMap = new HashMap<>();
    private int[][] tiles;
    private int n;
    private final int BLANK = 0;

    public Board(int[][] tiles) {
        int rowsLength = tiles.length;
        int colsLength = tiles[0].length;
        this.n = rowsLength;
        this.tiles = new int[rowsLength][colsLength];
        int x = 1;
        for (int i = 0; i < rowsLength; i += 1) {
            for (int j = 0; j < colsLength; j += 1) {
                this.tiles[i][j] = tiles[i][j];
                int[] arr = new int[]{i, j};
                goalHashMap.put(x, arr);
                actualHashMap.put(tiles[i][j], arr);
                x += 1;
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i >= n || i < 0) {
            throw new IndexOutOfBoundsException("row must be between n and 0");
        } else if (j >= n || j < 0) {
            throw new IndexOutOfBoundsException("column must be between n and 0");
        }
        return tiles[i][j];
    }

    public int size() {
        return n;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int result = 0;
        int x = 1;
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (tiles[i][j] != x && tiles[i][j] != BLANK) {
                    result += 1;
                }
                x += 1;
            }
        }
        return result;
    }


    public int manhattan() {
        int result = 0;
        int[] goalArray;
        int[] actualArray;
        int rowDifference;
        int colDifference;
        int difference;
        for (int i = 1; i < n * n; i += 1) {
            goalArray = goalHashMap.get(i);
            actualArray = actualHashMap.get(i);
            rowDifference = Math.abs(goalArray[0] - actualArray[0]);
            colDifference = Math.abs(goalArray[1] - actualArray[1]);
            difference = rowDifference + colDifference;
            result += difference;
        }
        return result;
    }


    public int estimatedDistanceToGoal() {
        return manhattan();
    }


    public boolean equals(Object y) {
        Board board2 = (Board) y;
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (tiles[i][j] != board2.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }



}
