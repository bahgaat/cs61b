package lab11.graphs;

/**
 *  @author Josh Hug
 */

//TODO solve cycles and then A *
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    public int[] parent;
    private boolean cycle = false;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[maze.V()];
    }

    @Override
    public void solve() {
        dfs(0);
    }

    private void dfs(int v) {
        marked[v] = true;
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parent[w] = v;
                announce();
                dfs(w);
            }
            if (marked[w] && parent[v] != w){
                edgeTo[w] = v;
                announce();
                break;
            }

        }
    }
}

