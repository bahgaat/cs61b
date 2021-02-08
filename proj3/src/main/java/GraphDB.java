/*import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import sun.awt.image.ImageWatched;
import sun.jvm.hotspot.oops.Array;
 */
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    private HashMap<Long, ArrayList<Long>> mapNodesIdToTheirAdjId = new HashMap<>();
    private HashMap<Long, Node> mapNodesIdToTheWholeNode = new HashMap<>();
    private ArrayList<Way> arrayListOfWays = new ArrayList<>();
    private HashMap<String, ArrayList<Node>> mapLocationNameToNodes = new HashMap<>();
    private Trie trie = new Trie();

    Node getNodeIdToTheWholeNode(long l) {
        return mapNodesIdToTheWholeNode.get(l);
    }

    ArrayList<Way> getArrayListOfWays() {
        return arrayListOfWays;
    }

    void addToTrie(String locationName) {
        trie.put(locationName);
    }

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Iterator<Long> verticesId = vertices().iterator();
        ArrayList<Long> unConnectedVerticesId = new ArrayList<>();
        long vertexId = 0;
        while (verticesId.hasNext()) {
            vertexId = verticesId.next();
            ArrayList<Long> adjacent = (ArrayList<Long>) adjacent(vertexId);
            if (adjacent.size() == 0) {
                unConnectedVerticesId.add(vertexId);
            }
        }
        for (int i = 0; i < unConnectedVerticesId.size(); i += 1) {
            removeNode(unConnectedVerticesId.get(i));
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return mapNodesIdToTheirAdjId.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> adjacent = mapNodesIdToTheirAdjId.get(v);
        return adjacent;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        long closestVertexId = 0;
        double distance1 = Integer.MAX_VALUE;
        Iterator<Long> verticesId = vertices().iterator();
        while (verticesId.hasNext()) {
            long vertexId = verticesId.next();
            double distance2 = distance(lon, lat, lon(vertexId), lat(vertexId));
            if (distance1 > distance2) {
                closestVertexId = vertexId;
                distance1 = distance2;
            }
        }
        return closestVertexId;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        Node node = mapNodesIdToTheWholeNode.get(v);
        String lon = node.getLon();
        return Double.parseDouble(lon);
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        Node node = mapNodesIdToTheWholeNode.get(v);
        String lat = node.getLat();
        return Double.parseDouble(lat);
    }

    void addNode(Node node) {
        String nodeId = node.getId();
        ArrayList<Long> arrayListOfAdjNodesId = new ArrayList<>();
        Long nodeIdToLong = Long.parseLong(nodeId);
        mapNodesIdToTheirAdjId.put(nodeIdToLong, arrayListOfAdjNodesId);
        mapNodesIdToTheWholeNode.put(nodeIdToLong, node);
    }

    /* by getting the nodes id. addEDge between those nodes. */
    void addEdge(String node1Id, String node2Id) {
        ArrayList<Long> arraylistOfAdjNode1Id = mapNodesIdToTheirAdjId.get(Long.parseLong(node1Id));
        arraylistOfAdjNode1Id.add(Long.parseLong(node2Id));
        ArrayList<Long> arraylistOfAdjNode2Id = mapNodesIdToTheirAdjId.get(Long.parseLong(node2Id));
        arraylistOfAdjNode2Id.add(Long.parseLong(node1Id));
    }

    void addWay(Way way) {
        arrayListOfWays.add(way);
    }


    void removeNode(Long vertexId) {
        mapNodesIdToTheWholeNode.remove(vertexId);
        mapNodesIdToTheirAdjId.remove(vertexId);
    }

    void addLocationNameToNode(String locationName, Node node) {
        if (mapLocationNameToNodes.containsKey(locationName)) {
            ArrayList arrayListOfNodes = mapLocationNameToNodes.get(locationName);
            arrayListOfNodes.add(node);
        } else {
            ArrayList arrayListOfNodes = new ArrayList();
            arrayListOfNodes.add(node);
            mapLocationNameToNodes.put(locationName, arrayListOfNodes);
        }
    }


    ArrayList<Node> getListOfNodesOfLocationName(String locationName) {
        List<String> listOfLocationsName = getLocationsByPrefix(locationName);
        if (listOfLocationsName.size() > 0) {
            locationName = listOfLocationsName.get(0);
        }
        ArrayList<Node> listOfNodes = mapLocationNameToNodes.get(locationName);
        return listOfNodes;
    }


    /* collect all locations that matches the prefix. */
    List<String> getLocationsByPrefix(String prefix) {
        return trie.keyWithPrefix(prefix);
    }

    /* collect all locations that has this locationName and put all its information
    in a map. */
    List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new ArrayList<>();
        ArrayList<Node> listOfNodes = getListOfNodesOfLocationName(locationName);
        if (listOfNodes != null) {
            for (int i = 0; i < listOfNodes.size(); i += 1) {
                Map<String, Object> map = new HashMap<>();
                Node node = listOfNodes.get(i);
                map.put("id", Long.parseLong(node.getId()));
                map.put("lon", Double.parseDouble(node.getLon()));
                map.put("lat", Double.parseDouble(node.getLat()));
                map.put("name", node.getLocationName());
                result.add(map);
            }
        }
        return result;
    }

}
