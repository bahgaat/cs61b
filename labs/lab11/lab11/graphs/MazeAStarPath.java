package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {

        MinPQ<Integer> fringe = new MinPQ<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if ((h(o1) + o1) < (h(o2) + o2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        fringe.insert(s);
        marked[s] = true;
        s = fringe.delMin();
        while (s != t) {
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    fringe.insert(w);
                    marked[w] = true;
                    announce();
                    edgeTo[w] = s;
                    distTo[w] = distTo[s] + 1;
                    announce();
                }
            }
            s = fringe.delMin();
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

