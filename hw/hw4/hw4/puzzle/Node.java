package hw4.puzzle;
import java.util.*;

public class Node implements Comparable<Node> {
    WorldState worldState;
    int numberOfMovesFromTheFirstNode;
    Node parentNode;
    Node grandParent;
    boolean distanceComputed = false;
    int estimatedDistance;

    public Node(WorldState worldState, int  numberOfMovesFromTheFirstNode,
                Node parentNode, Node grandParent) {
        this.worldState = worldState;
        this.numberOfMovesFromTheFirstNode = numberOfMovesFromTheFirstNode;
        this.parentNode = parentNode;
        this.grandParent = grandParent;
    }



    @Override
    public int compareTo(Node node2) {
        int totalDistanceNode1 = helperCompare(this);
        int totalDistanceNode2 = helperCompare(node2);
        int answer = totalDistanceNode1 - totalDistanceNode2;
        return answer;
    }

    private int helperCompare(Node node) {
        int movesNodeFromFirstNode = node.numberOfMovesFromTheFirstNode;
        if (node.distanceComputed == false) {
            node.estimatedDistance = node.worldState.estimatedDistanceToGoal();
            node.distanceComputed = true;
        }

        node.estimatedDistance = node.worldState.estimatedDistanceToGoal();
        int totalDistanceNode = movesNodeFromFirstNode + node.estimatedDistance;
        return totalDistanceNode;
    }


}


