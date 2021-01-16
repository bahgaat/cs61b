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
        while (!fringe.isEmpty()) {
            int s = fringe.poll();
            if (s == t) {
                break;
            }
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

        }

        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
    }


    @Override
    public void solve() {
        bfs();
    }
}

