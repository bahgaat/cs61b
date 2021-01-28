import java.util.Comparator;

public class NodeComparator implements Comparator<Long> {
    private long closestNodeIdToStartPointId;
    private long closestNodeIdToEndPointId;
    private GraphDB g;

    public NodeComparator(long closestNodeIdToStartPointId, long closestNodeIdToEndPointId, GraphDB g) {
        this.closestNodeIdToStartPointId = closestNodeIdToStartPointId;
        this.closestNodeIdToEndPointId = closestNodeIdToEndPointId;
        this.g = g;
    }

    @Override
    public int compare(Long node1Id, Long node2Id) {
        double node1shortestPathIdFromClosest = g.distance(node1Id, closestNodeIdToStartPointId);
        double node1heuristicDist = g.distance(node1Id, closestNodeIdToEndPointId);
        double totalDistanceNode1 = node1shortestPathIdFromClosest + node1heuristicDist;
        double node2shortestPathIdFromClosest = g.distance(node2Id, closestNodeIdToStartPointId);
        double node2heuristicDist = g.distance(node2Id, closestNodeIdToEndPointId);
        double totalDistanceNode2 = node2shortestPathIdFromClosest + node2heuristicDist;
        if (totalDistanceNode2 > totalDistanceNode1) {
            return 1;
        } else {
            return -1;
        }
    }
}
