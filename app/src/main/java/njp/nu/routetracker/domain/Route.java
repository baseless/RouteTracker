package njp.nu.routetracker.domain;

/**
 * Created by Andreas Svensson on 2015-03-23.
 */

public class Route {
    private int routeID;
    private String stopTime;
    private String startTime;

    public Route(String startTime) {
        this.startTime = startTime;
    }
    public Route(String startTime, String stopTime, int routeID) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.routeID = routeID;
    }

    public int getRouteID() {
        return routeID;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getStopTime() {
        return stopTime;
    }
}


