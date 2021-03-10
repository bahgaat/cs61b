package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState  {
    private int[][] tiles;
    private int n;
    private final int BLANK = 0;

    public Board(int[][] tiles) {
        int rowsLength = tiles.length;
        int colsLength = tiles[0].length;
        this.n = rowsLength;
        this.tiles = new int[rowsLength][colsLength];
        for (int i = 0; i < rowsLength; i += 1) {
            for (int j = 0; j < colsLength; j += 1) {
                this.tiles[i][j] = tiles[i][j];
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
        int num;
        int numMinusOne;
        int row;
        int col;
        int rowDifference;
        int colDifference;
        int difference;
        for (int i = 0; i < n ; i += 1) {
            for (int j = 0; j < n; j += 1) {
                num = tileAt(i, j);
                if (num != 0) {
                    numMinusOne = num - 1;
                    row = numMinusOne / n;
                    col = num - 1 - (n * row);
                    rowDifference = Math.abs(row - i);
                    colDifference = Math.abs(col - j);
                    difference = rowDifference + colDifference;
                    result += difference;
                }
            }
        }
        return result;
    }


    public int estimatedDistanceToGoal() {
        return manhattan();
    }


    public boolean equals(Object y) {
        if (y == null) {
            return false;
        } else if (y.getClass() == this.getClass()) {
            Board comp = (Board) y;
            if (comp.size() == this.size()) {
                for (int i = 0; i < this.size(); i++) {
                    for (int j = 0; j < this.size(); j++) {
                        if (comp.tileAt(i, j) != this.tileAt(i, j)) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }


    public int hashCode() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum = (sum + tiles[i][j]) * 31;
            }
        }
        return sum;
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
