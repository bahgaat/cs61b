
import java.util.ArrayList;

class Way {
    private String id;
    private String wayName;
    private ArrayList<String> arrayListOfConnectedNodesId;
    private boolean hasName = false;
    private boolean isValidWay = false;

    public String getId() {
        return id;
    }

    boolean hasName() {
        return hasName;
    }

    boolean isValidWay() {
        return isValidWay;
    }

    void setValidWay(boolean validWay) {
        isValidWay = validWay;
    }

    void addToArrOfNodes(String node) {
        arrayListOfConnectedNodesId.add(node);
    }

    void setHasName(boolean hasName) {
        this.hasName = hasName;
    }

    void setWayName(String wayName) {
        this.wayName = wayName;
    }

    String getWayName() {
        return wayName;
    }

    public ArrayList<String> getArrayListOfConnectedNodesId() {
        return arrayListOfConnectedNodesId;
    }

    Way(String id, ArrayList<String> arrayListOfConnectedNodes) {
        this.id = id;
        this.arrayListOfConnectedNodesId = arrayListOfConnectedNodes;
    }

}