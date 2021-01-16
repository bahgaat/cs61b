package hw4.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    int minMovesToReachGoal;
    private Stack<WorldState> path;
    ArrayList<WorldState> sequenceFromInitialToGoal;
    int debug = 0;

    public Solver(WorldState initial) {
        Node goal = null;
        sequenceFromInitialToGoal = new ArrayList<WorldState>();
        sequenceFromInitialToGoal.add(initial);
        minMovesToReachGoal = 0;
        path = new Stack<>();
        Node node = new Node(initial, minMovesToReachGoal, null, null);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        priorityQueue.add(node);
        node = priorityQueue.remove();
        while (!node.worldState.isGoal()) {
            minMovesToReachGoal += 1;
            Iterable<WorldState> neighbors = node.worldState.neighbors();
            iterateOverTheNeighbors(neighbors, priorityQueue, node);
            node = priorityQueue.remove();
            sequenceFromInitialToGoal.add(node.worldState);
        }
        goal = node;
        while (goal != null) {
            path.push(goal.worldState);
            goal = goal.parentNode;
        }

    }

    private void iterateOverTheNeighbors(Iterable<WorldState> neighbors, PriorityQueue<Node> priorityQueue,
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
                    priorityQueue.add(newNode);
                    debug += 1;
                }
            } else {
                priorityQueue.add(newNode);
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