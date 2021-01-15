package hw4.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Solver {
    int minMovesToReachGoal;
    ArrayList<WorldState> sequenceFromInitialToGoal;
    int debug = 0;


    public Solver(WorldState initial) {
        sequenceFromInitialToGoal = new ArrayList<WorldState>();
        sequenceFromInitialToGoal.add(initial);
        minMovesToReachGoal = 0;
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
        return minMovesToReachGoal;
    }

    public Iterable<WorldState> solution() {
        return sequenceFromInitialToGoal;
    }

}

