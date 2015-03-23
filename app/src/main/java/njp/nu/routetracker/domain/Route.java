package njp.nu.routetracker.domain;

/**
 * Created by Andreas Svensson on 2015-03-23.
 */

public class Route {
    private int routeID;
    private int stopTime;
    private int startTime;

    public Route() {}
    public Route(int routeID) {
        this.routeID = routeID;
    }
    public Route(int routeID, int startTime) {
        this.routeID = routeID;
        this.startTime = startTime;
    }
    public Route(int routeID, int startTime, int stopTime) {
        this.routeID = routeID;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }
    public int getRouteID() {
        return routeID;
    }
    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public long getStopTime() {
        return stopTime;
    }
    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }
}


