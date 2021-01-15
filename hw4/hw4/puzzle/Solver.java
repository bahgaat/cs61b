package hw4.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Solver {
    int minMovesToReachGoal;
    ArrayList<WorldState> sequenceFromInitialToGoal;


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

        Iterator<WorldState> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            WorldState worldState = iterator.next();
            Node newNode = new Node(worldState, minMovesToReachGoal, node, node.parentNode);
            if (newNode.grandParent != null) {
                WorldState worldStateGrandParent = newNode.grandParent.worldState;
                if (!worldState.equals(worldStateGrandParent)) {
                    priorityQueue.add(newNode);
                }
            } else {
                priorityQueue.add(newNode);
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

