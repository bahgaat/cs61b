class Node {
    /* A node class. */
    private String id;
    private String lon;
    private String lat;
    private boolean isLocation = false;
    private String locationName;

    String getLocationName() {
        return locationName;
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
