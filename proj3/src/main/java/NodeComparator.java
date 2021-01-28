import java.util.Comparator;
import java.util.HashMap;

public class NodeComparator implements Comparator<Long> {
    private HashMap<Long, Double> mapNodeIdToBestDist;
    private long closestNodeIdToEndPointId;
    private GraphDB g;
   /* private HashMap<Long, Double> mapNodeIdToPriority = new HashMap<>();*/

    public NodeComparator(HashMap<Long, Double> mapNodeIdToBestDist,
                          long closestNodeIdToEndPointId, GraphDB g) {

        this.mapNodeIdToBestDist = mapNodeIdToBestDist;
        this.closestNodeIdToEndPointId = closestNodeIdToEndPointId;
        this.g = g;
    }

    @Override
    public int compare(Long node1Id, Long node2Id) {
        double DistanceNode1 = mapNodeIdToBestDist.get(node1Id);
        double nodeHeuristicDis1 = g.distance(node1Id, closestNodeIdToEndPointId);
        double DistanceNode2 = mapNodeIdToBestDist.get(node2Id);
        double nodeHeuristicDis2 = g.distance(node2Id, closestNodeIdToEndPointId);
        double totalDistance1 = DistanceNode1 + nodeHeuristicDis1;
        double totalDistance2 = DistanceNode2 + nodeHeuristicDis2;
        if (totalDistance1 > totalDistance2) {
            return 1;
        } else if (totalDistance2 > totalDistance1) {
            return -1;
        } else {
            return 0;
        }

        /*
        if (mapNodeIdToPriority.containsKey(node2Id)) {
            totalDistanceNode2 = mapNodeIdToPriority.get(node2Id);
        } else {
            totalDistanceNode2 = helperCompare(node2Id);
        }
        if (totalDistanceNode2 < totalDistanceNode1) {
            return 1;
        } else {
            return -1;
        }

         */

    }

}
