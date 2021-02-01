class Node {
    /* A node class. */
    private String id;
    private String lon;
    private String lat;
    private boolean isLocation = false;
    private String locationName;
    private String wayName;
    private boolean hasWayName = false;

    boolean hasWayName() {
        return hasWayName;
    }

    void setHasWayName(boolean hasWayName) {
        this.hasWayName = hasWayName;
    }

    String getWayName() {
        return wayName;
    }

    void setWayName(String wayName) {
        this.wayName = wayName;
    }

    String getId() {
        return id;
    }

    void isLocation() {
        isLocation = true;
    }

    void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    String getLon() {
        return lon;
    }

    String getLat() {
        return lat;
    }

    Node(String id, String lon, String lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
    }



}
