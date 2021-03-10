package lab11.graphs;

import java.util.*;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> fringe = new LinkedList<>();
        fringe.add(s);
        marked[s] = true;
        int s = fringe.poll();
        while (s != t) {
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    fringe.add(w);
                    marked[w] = true;
                    announce();
                    edgeTo[w] = s;
                    distTo[w] = distTo[s] + 1;
                    announce();
                }
            }
            s = fringe.poll();

        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

