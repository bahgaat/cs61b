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
    private static HashMap<Long, Double> nodeIdToBestDist;
    private static HashMap<Long, Long> nodeIdToItsParent;
    private static boolean reached = false;
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

        nodeIdToBestDist  = new HashMap<>();
        nodeIdToItsParent = new HashMap<>();
        long closestNodeIdToStartPointId = g.closest(stlon, stlat);
        long closestNodeIdToEndPointId = g.closest(destlon, destlat);
        ExtrinsicPQ<Long> pq = new ArrayHeap<> ();
        addNodeIdToBestDist(g, closestNodeIdToStartPointId);
        pq.insert(closestNodeIdToStartPointId, 0);
        long nodeId = obtainTheClosestNodeToEndPoint(pq,
                closestNodeIdToEndPointId, closestNodeIdToStartPointId, g);
        if (reached == true) {
            return listOfNodesId(nodeId, closestNodeIdToStartPointId);
        } else {
            return new ArrayList<>();
        }
    }

    /* put start distance to each node id in the map. */
    private static void addNodeIdToBestDist(GraphDB g, long closestNodeIdToStartPointId) {

        Iterable<Long> vertices = g.vertices();
        double pInfiniteDouble = Double.POSITIVE_INFINITY;
        Iterator<Long> iterator = vertices.iterator();
        Long nodeId;
        while (iterator.hasNext()) {
            nodeId = iterator.next();
            if (nodeId.equals(closestNodeIdToStartPointId)) {
                nodeIdToBestDist.put(nodeId, (double) 0);
            } else {
                nodeIdToBestDist.put(nodeId, pInfiniteDouble);
            }
        }
    }


    private static long obtainTheClosestNodeToEndPoint(ExtrinsicPQ<Long> pq, long closestNodeIdToEndPointId,
                                                       long closestNodeIdToStartPointId,
                                                       GraphDB g) {

        Long nodeId = closestNodeIdToStartPointId;
        while (pq.size() > 0) {
            nodeId = pq.removeMin();
            if (nodeId.equals(closestNodeIdToEndPointId)) {
                reached = true;
                break;
            }
            Iterable<Long> neighbors = g.adjacent(nodeId);
            addNeighborsToPq(neighbors, pq, nodeId, g, closestNodeIdToEndPointId);
        }
        return nodeId;
    }



    private static void addNeighborsToPq(Iterable<Long> neighbors, ExtrinsicPQ<Long> pq,
                                         long parentNodeId, GraphDB g, long closestNodeIdToEndPointId) {


        Iterator<Long> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            long nodeId = iterator.next();
            double distFormStartToParen = nodeIdToBestDist.get(parentNodeId);
            double totalDist = distFormStartToParen + g.distance(parentNodeId, nodeId);
            double bestDist = nodeIdToBestDist.get(nodeId);
            if (totalDist < bestDist) {
                nodeIdToBestDist.replace(nodeId, totalDist);
                double nodeHeuristicDis = g.distance(nodeId, closestNodeIdToEndPointId);
                double priority = totalDist + nodeHeuristicDis;
                pq.insert(nodeId, priority);
                nodeIdToItsParent.put(nodeId, parentNodeId);
            }
        }
    }


    /* return list of nodes id in the visited order. */
    private static List<Long> listOfNodesId(Long nodeId, Long closestNodeIdToStartPointId) {

        ArrayList<Long> reversePath = new ArrayList<>();
        while (!nodeId.equals(closestNodeIdToStartPointId)) {
            reversePath.add(nodeId);
            nodeId = nodeIdToItsParent.get(nodeId);
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
        ArrayList<NavigationDirection> listOfNavigationDirections = new ArrayList<>();
        double totalDistance = 0.0;
        String wayName = "";
        long previousNodeId = 0;
        long firstId = 0;
        int direction = 0;
        for (int i = 0; i < route.size(); i += 1) {
            Long currentNodeId = route.get(i);
            if (i == 0) {
                wayName = findCurrentWayName(currentNodeId, g);
                firstId = currentNodeId;
            } else {
                boolean theyAreInSameWay = checkIfSameWay(currentNodeId, g, wayName);
                if (theyAreInSameWay && i != (route.size() - 1)) {
                    totalDistance += g.distance(previousNodeId, currentNodeId);
                } else if (i == (route.size() - 1)) {
                    totalDistance += g.distance(previousNodeId, currentNodeId);
                    addNavigation(listOfNavigationDirections, totalDistance, wayName, direction);
                } else {
                    addNavigation(listOfNavigationDirections, totalDistance, wayName, direction);
                    wayName = findCurrentWayName(currentNodeId, g);
                    totalDistance = g.distance(previousNodeId, currentNodeId);
                    direction = seeWhichDirectionToGo(previousNodeId, currentNodeId, g, firstId);
                    firstId = previousNodeId;
                }
            }
            previousNodeId = currentNodeId;
        }
        return listOfNavigationDirections;
    }

    private static void addNavigation(ArrayList<NavigationDirection> listOfNavigationDirections,
                                      double totalDistance, String wayName, int direction) {

        NavigationDirection navigationDirection = new NavigationDirection();
        navigationDirection.direction = direction;
        navigationDirection.distance = totalDistance;
        navigationDirection.way = wayName;
        listOfNavigationDirections.add(navigationDirection);
    }

    private static boolean checkIfSameWay(Long currentNodeId, GraphDB g, String wayName) {

        ArrayList<Way> arrayListOfWays = g.getArrayListOfWays();
        for (int i = 0; i < arrayListOfWays.size(); i += 1) {
            Way way = arrayListOfWays.get(i);
            ArrayList connectedNodesId = way.getArrayListOfConnectedNodesId();
            for (int j = 0; j < connectedNodesId.size(); j += 1) {
                Long nodeId = Long.parseLong((String) connectedNodesId.get(j));
                if (nodeId.equals(currentNodeId)) {
                    String newWayName = getWayName(way);
                    if (newWayName.equals(wayName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    private static String findCurrentWayName(Long currentNodeId, GraphDB g) {
        ArrayList<Way> arrayListOfWays = g.getArrayListOfWays();
        for (int i = 0; i < arrayListOfWays.size(); i += 1) {
            Way way = arrayListOfWays.get(i);
            ArrayList connectedNodesId = way.getArrayListOfConnectedNodesId();
            for (int j = 0; j < connectedNodesId.size(); j += 1) {
                Long nodeId = Long.parseLong((String) connectedNodesId.get(j));
                if (nodeId.equals(currentNodeId)) {
                    return getWayName(way);
                }
            }
        }
        return "";
    }


    private static String getWayName(Way way) {
        String wayName;
        if (way.hasName()) {
            wayName = way.getWayName();
        } else {
            wayName = "unknown road";
        }
        return wayName;
    }



    private static int seeWhichDirectionToGo(long previousNodeId, long currentNodeId,
                                             GraphDB g, long firstId) {
        int direction = 0;
        double preBearing = g.bearing(firstId, previousNodeId);
        double currBearing = g.bearing(previousNodeId, currentNodeId);
        double relativeBearing = currBearing - preBearing;
        if (relativeBearing >= -15.0 && relativeBearing <= 15.0) {
            direction = 1;
        } else if (relativeBearing >=30.0 && relativeBearing <=30.0) {
            direction = setDirection(relativeBearing, 2, 3); /* this is correct.*/
        } else if (relativeBearing >=-100.0 && relativeBearing <=100.0) {
            direction = setDirection(relativeBearing, 5, 4); /* this is correct. */
        } else {
            direction = setDirection(relativeBearing, 7, 6);
        }
        return direction;
    }

    private static int setDirection(double relativeBearing, int left, int right) {
        int direction = 0;
        if (relativeBearing < 0.0) {
            direction = left;
        } else {
            direction = right;
        }
        return direction;
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
