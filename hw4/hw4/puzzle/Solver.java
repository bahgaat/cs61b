package hw4.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    int minMovesToReachGoal;
    private Stack<WorldState> path;
    int debug = 0;

    public Solver(WorldState initial) {
        Node goal = null;
        minMovesToReachGoal = 0;
        path = new Stack<>();
        MinPQ<Node> priorityQueue = new MinPQ<>();
        Node node = new Node(initial, minMovesToReachGoal, null, null);
        priorityQueue.insert(node);
        while (!priorityQueue.isEmpty()) {
            node = priorityQueue.delMin();
            minMovesToReachGoal += 1;
            if (node.worldState.isGoal()) {
                goal = node;
                break;
            }
            Iterable<WorldState> neighbors = node.worldState.neighbors();
            iterateOverTheNeighbors(neighbors, priorityQueue, node);
            node = priorityQueue.delMin();
        }
        while (goal != null) {
            path.push(goal.worldState);
            goal = goal.parentNode;
        }

    }

    private void iterateOverTheNeighbors(Iterable<WorldState> neighbors, MinPQ<Node> priorityQueue,
                                         Node node) {

        WorldState worldState;
        Node newNode;
        Iterator<WorldState> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            worldState = iterator.next();
            newNode = new Node(worldState, minMovesToReachGoal, node, node.parentNode);
            if (newNode.grandParent != null) {
                WorldState worldStateGrandParent = newNode.grandParent.worldState;
                if (!worldState.equals(worldStateGrandParent)) {
                    priorityQueue.insert(newNode);
                    debug += 1;
                }
            } else {
                priorityQueue.insert(newNode);
                debug += 1;
            }
        }
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return path;
    }

}