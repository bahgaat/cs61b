import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    private static HashMap<Long, Double> mapNodeIdToBestDist;
    private static HashMap<Long, Long> mapNodeIdToItsParent;


    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        mapNodeIdToBestDist  = new HashMap<>();
        mapNodeIdToItsParent = new HashMap<>();
        long closestNodeIdToStartPointId = g.closest(stlon, stlat);
        long closestNodeIdToEndPointId = g.closest(destlon, destlat);
        ExtrinsicPQ<Long> pq = new ArrayHeap<> ();
        addToMapNodeIdToBestDist(g, closestNodeIdToStartPointId, pq);
        long nodeId = pq.removeMin();
        long goalNodeId = closestNodeIdToEndPointId;
        nodeId = obtainTheClosestNodeToEndPoint(nodeId, goalNodeId, pq,
                closestNodeIdToEndPointId, closestNodeIdToStartPointId, g);
        return listOfNodesId(nodeId, closestNodeIdToStartPointId);
    }

    private static long obtainTheClosestNodeToEndPoint(long nodeId,  long goalNodeId
            , ExtrinsicPQ<Long> pq, long closestNodeIdToEndPointId, long closestNodeIdToStartPointId,
                                                       GraphDB g) {

        while (nodeId != goalNodeId && pq.size() > 0) {
            Iterable<Long> neighbors = g.adjacent(nodeId);
            addNeighborsToPq(neighbors, pq, nodeId, g, closestNodeIdToStartPointId,
                    closestNodeIdToEndPointId);
            nodeId = pq.removeMin();
        }
        return nodeId;
    }


    private static void addToMapNodeIdToBestDist(GraphDB g, long closestNodeIdToStartPointId,
                                                 ExtrinsicPQ<Long> pq) {

        Iterable<Long> vertices = g.vertices();
        double pInfiniteDouble = Double.POSITIVE_INFINITY;
        Iterator<Long> iterator = vertices.iterator();
        long nodeId;
        while (iterator.hasNext()) {
            nodeId = iterator.next();
            if (nodeId == closestNodeIdToStartPointId) {
                mapNodeIdToBestDist.put(nodeId, (double) 0);
                pq.insert(closestNodeIdToStartPointId, 0);
            } else {
                mapNodeIdToBestDist.put(nodeId, pInfiniteDouble);
                pq.insert(nodeId, pInfiniteDouble);
            }
        }
    }

    private static void addNeighborsToPq(Iterable<Long> neighbors, ExtrinsicPQ<Long> pq,
                                         long parentNodeId,
                                         GraphDB g, long closestNodeIdToStartPointId,
                                         long closestNodeIdToEndPointId) {

        Iterator<Long> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            long nodeId = iterator.next();
            double distFormStartToParen = mapNodeIdToBestDist.get(parentNodeId);
            double totalDist = distFormStartToParen + g.distance(parentNodeId, nodeId);
            double bestDist = mapNodeIdToBestDist.get(nodeId);
            if (totalDist < bestDist) {
                mapNodeIdToBestDist.replace(nodeId, totalDist);
                double nodeHeuristicDis = g.distance(nodeId, closestNodeIdToEndPointId);
                double priority = totalDist + nodeHeuristicDis;
                pq.changePriority(nodeId, priority);
                mapNodeIdToItsParent.put(nodeId, parentNodeId);
            }
        }
    }

    private static List<Long> listOfNodesId(long nodeId, long closestNodeIdToStartPointId) {

        ArrayList<Long> reversePath = new ArrayList<>();
        while (nodeId != closestNodeIdToStartPointId) {
            reversePath.add(nodeId);
            nodeId = mapNodeIdToItsParent.get(nodeId);
        }
        reversePath.add(nodeId);
        Collections.reverse(reversePath);
        return reversePath;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}


